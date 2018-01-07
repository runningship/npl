<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta name="description" content="HTML5 Audio Spectrum Visualizer">
        <title>HTML5 Audio API showcase | Audio visualizer</title>
        <link type="text/css" rel="stylesheet" href="style/style.css">
    </head>
    <body>
        <div id="wrapper">
            <div id="fileWrapper" class="file_wrapper">
                <div id="info">
                    HTML5 Audio API showcase | An Audio Viusalizer
                </div>
                <label for="uploadedFile">Drag&drop or select a file to play:</label>
                <input type="file" id="uploadedFile"></input>
            </div>
            <div id="visualizer_wrapper">
                <canvas id='canvas' width="1024" height="350"></canvas>
            </div>
        </div>
        <footer>
            <small>Star me on <a href="https://github.com/Wayou/HTML5_Audio_Visualizer"  target="_blank">GitHub</a></small>
        </footer>
        <script type="text/javascript" src="js/html5_audio_visualizer.js"></script>
    </body>
</html>