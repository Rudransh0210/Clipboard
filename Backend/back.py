from crypt import methods
from flask import Flask
from flask import request


app = Flask(__name__)

@app.route('/post', methods=["POST"])
def setcontent():
    content = request.form['clipboard_content']
    text_file = open('D:\Clipboard\Backend')
    text_file.write(content)
    text_file.close()

@app.route('/get', methods= ["POST"])
def postcontent():
    text_file = open('D:\Clipboard\Backend')
    content = text_file.read()
    text_file.close()
    return(content)

if __name__=="__main__":
    app.run(host='0.0.0.0')
