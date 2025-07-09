import sys
from PIL import Image
import pytesseract
import cv2

# Tambahkan path ke tesseract.exe
pytesseract.pytesseract.tesseract_cmd = r"C:\Program Files\Tesseract-OCR\tesseract.exe"

def preprocess_image(image_path):
    img = cv2.imread(image_path)
    if img is None:
        raise Exception(f"Image {image_path} tidak ditemukan.")
    gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
    _, thresh = cv2.threshold(gray, 0, 255, cv2.THRESH_BINARY + cv2.THRESH_OTSU)
    return Image.fromarray(thresh)

def main():
    if len(sys.argv) < 3:
        print("Usage: python detect_toast.py <image_path> <expected_text>")
        sys.exit(1)

    image_path = sys.argv[1]
    expected = sys.argv[2]

    try:
        img = preprocess_image(image_path)
    except Exception as e:
        print("Error:", e)
        sys.exit(1)

    text = pytesseract.image_to_string(img, lang='ind').strip()
    print("Detected text:", text)

    if expected.lower() in text.lower():
        sys.exit(0)  # SUCCESS
    else:
        sys.exit(1)  # FAILED

if __name__ == "__main__":
    main()
