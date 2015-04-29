$(document).ready(function () {
    var str = ["../images/213.jpg", "../images/P1030123.jpg", "../images/images.jpg"];
    var testdiv = document.getElementById("testdiv");
    testdiv.innerHTML=" <div class='layout_default'><p class='info'><span class='date'></span> <span class='author'></span></p><div class='image_container'><a href='"+str[0]+"' rel='lightbox[ostec]' title='Õ¾³¤ËØ²ÄÍøÕ¾½¨Éè'> <img src='images/test.jpg' alt='Õ¾³¤ËØ²ÄÍøÕ¾½¨Éè' > </a></div></div>"
    +"<div class='layout_default'><p class='info'><span class='date'></span> <span class='author'></span></p><div class='image_container'> <a href='"+str[1]+"' rel='lightbox[ostec]'> <img src='images/test.jpg' alt='Õ¾³¤ËØ²ÄHTMLÑ§Ï°' > </a> </div></div>"
    +"<div class='layout_default'><p class='info'><span class='date'></span> <span class='author'></span></p><div class='image_container'><a href='" + str[2] + "' rel='lightbox[ostec]' title='Õ¾³¤ËØ²ÄÍøÕ¾½¨Éè'> <img src='images/test.jpg' alt='Õ¾³¤ËØ²ÄÍøÕ¾½¨Éè' > </a></div></div>";
    
});
