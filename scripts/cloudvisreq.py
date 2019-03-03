import  io
from google.cloud import vision
from google.cloud.vision import types
import os
os.environ["GOOGLE_APPLICATION_CREDENTIALS"] = "/Users/liamkelly/hackathons/Hoohacks2k19/SyllaSnap/app/hoohacks2019-9cba8b1ce5e3.json"

def get_left_top_right_bottom(vertices):
    left = None
    right = None
    top = None
    bottom = None

    for vertex in vertices:
        if left is None:
            left = vertex.x
            right = vertex.x
        if top is None:
            top = vertex.y
            bottom = vertex.y
        else:
            if vertex.x < left:
                left = vertex.x
            if vertex.x > right:
                right = vertex.x
            if vertex.y < top:
                top = vertex.y
            if vertex.y > bottom:
                bottom = vertex.y

    return str(left) + "," + str(top) + "," + str(right) + "," + str(bottom)


def detect_text(file):
    """Detects text in the file."""
    client = vision.ImageAnnotatorClient()

    with io.open(file, 'rb') as image_file:
        content = image_file.read()

    image = types.Image(content=content)

    response = client.text_detection(image=image)
    texts = response.text_annotations
    print('Texts:')

    r = ""
    for text in texts:
        r += '"' + text.description + '"' + get_left_top_right_bottom(text.bounding_poly.vertices) + "\n"

    print(r)

file_name = "ex1.jpg"
detect_text(file_name)
