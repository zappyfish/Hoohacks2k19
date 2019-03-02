import  io
from google.cloud import vision
from google.cloud.vision import types
import os
os.environ["GOOGLE_APPLICATION_CREDENTIALS"] = "/Users/liamkelly/hackathons/Hoohacks2k19/SyllaSnap/app/hoohacks2019-9cba8b1ce5e3.json"

def detect_text(file):
    """Detects text in the file."""
    client = vision.ImageAnnotatorClient()

    with io.open(file, 'rb') as image_file:
        content = image_file.read()

    image = types.Image(content=content)

    response = client.text_detection(image=image)
    texts = response.text_annotations
    print('Texts:')

    r = []
    for text in texts:
        print('\n"{}"'.format(text.description))

        vertices = (['({},{})'.format(vertex.x, vertex.y)
                    for vertex in text.bounding_poly.vertices])

        print('bounds: {}'.format(','.join(vertices)))

file_name = "ex4.png"
detect_text(file_name)
