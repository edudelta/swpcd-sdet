import datetime

import pytest
import requests
from requests import Response

from config import config

API_KEY_PARAM = {'api_key': config.API_KEY}
APOD_URL = f'{config.API_URL}/planetary/apod'

DATE_FORMAT = "%Y-%m-%d"


def test_get_picture_of_the_day():
    r = requests.get(APOD_URL, params=API_KEY_PARAM)

    validate_successful_response(r, 5)


def test_get_picture_of_specific_dates():
    params = {'start_date': '2022-05-05', 'end_date': '2022-05-20'}
    params.update(API_KEY_PARAM)
    r = requests.get(APOD_URL, params=params)

    validate_successful_response(r, 16)


@pytest.mark.parametrize("number_of_images", [1, 100])
def test_get_pictures_defining_limit_number_of_images(number_of_images):
    params = {'count': number_of_images}
    params.update(API_KEY_PARAM)
    r = requests.get(APOD_URL, params=params)

    validate_successful_response(r, number_of_images)


@pytest.mark.parametrize("count_value", [0, 101])
def test_get_pictures_bad_request_with_invalid_count(count_value):
    params = {'count': count_value}
    params.update(API_KEY_PARAM)
    r = requests.get(APOD_URL, params=params)

    assert r.status_code == 400
    json_content = r.json()
    assert json_content["code"] == 400
    assert json_content["msg"] == "Count must be positive and cannot exceed 100"


# Instead of using specific date, I'm looking for a day with video
def test_get_pictures_with_thumbs():
    limit_of_retries = 4
    range_days = 30

    today = datetime.datetime.now()
    params = {'thumbs': True}
    params.update(API_KEY_PARAM)

    videos = list()

    retry_same = False
    count = 1
    while count <= limit_of_retries:
        start_date = today + datetime.timedelta(days=-(range_days * count))
        end_date = today + datetime.timedelta(days=-(range_days * (count - 1)))
        dates = {'start_date': start_date.strftime(DATE_FORMAT), 'end_date': end_date.strftime(DATE_FORMAT)}
        params.update(dates)
        r = requests.get(APOD_URL, params=params)

        if r.status_code != 200:
            if retry_same:
                pytest.skip("Two consecutive errors from server side")
            else:
                retry_same = True
                continue
        videos = list(filter(lambda x: x['media_type'] == "video", r.json()))
        if len(videos) > 0:
            break
        count += 1

    else:
        pytest.skip("Unable to find a day with video")

    for v in videos:
        assert v['thumbnail_url'] is not None
        assert v['thumbnail_url'].startswith("https://img.youtube.com/vi/")


invalid_params = [
    (
        {'start_date': '2023-04-04', 'date': '2023-04-04'},
        "Bad Request: invalid field combination passed. Allowed request fields for apod method are "
        "'concept_tags', 'date', 'hd', 'count', 'start_date', 'end_date', 'thumbs'"
    ),
    (
        {'end_date': '2023-04-04', 'date': '2023-04-04'},
        "Bad Request: invalid field combination passed. Allowed request fields for apod method are "
        "'concept_tags', 'date', 'hd', 'count', 'start_date', 'end_date', 'thumbs'"
    ),
    (
        {'start_date': '2023-04-04', 'end_date': '2023-04-04', 'count': 1},
        "Bad Request: invalid field combination passed. Allowed request fields for apod method are "
        "'concept_tags', 'date', 'hd', 'count', 'start_date', 'end_date', 'thumbs'"
    ),
    (
        {
            'start_date': '2023-06-25',
            'end_date': (datetime.datetime.now() + datetime.timedelta(days=+1)).strftime(DATE_FORMAT)
        },
        f'Date must be between Jun 16, 1995 and {datetime.datetime.now().strftime("%b %d, %Y")}.'
    )
]


@pytest.mark.parametrize("params,message", invalid_params)
def test_get_pictures_results_bad_request_with_invalid_params(params, message):
    params.update(API_KEY_PARAM)
    r = requests.get(APOD_URL, params=params)
    assert r.status_code == 400
    json_content = r.json()
    assert json_content["code"] == 400
    assert json_content["msg"] == message


def test_get_pictures_without_api_key():
    r = requests.get(APOD_URL)
    assert r.status_code == 403
    json_content = r.json()
    assert json_content["error"]["code"] == "API_KEY_MISSING"
    assert json_content["error"]["message"] == "No api_key was supplied. Get one at https://api.nasa.gov:443"


def validate_successful_response(r: Response, number_of_images: int):
    if number_of_images < 1:
        raise ValueError('number_of_images should be at least 1 to validate response images')

    assert r.status_code == 200, "Request was not successful"
    json_content = r.json()
    assert isinstance(json_content, list), f'Expected to response include {number_of_images} images'
    assert len(
        json_content) == number_of_images, f'Expected to response include {number_of_images} images, ' \
                                           f'but got {len(r.text)}'
