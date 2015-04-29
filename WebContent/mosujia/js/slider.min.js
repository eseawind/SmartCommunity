/*
--------------------------------
SliderJS - jQuery Slider with CSS Transitions
--------------------------------
+ https://github.com/pinceladasdaweb/SliderJS
+ version 1.0.1
+ Copyright 2013 Pedro Rogerio
+ Licensed under the MIT license

+ Documentation: https://github.com/pinceladasdaweb/SliderJS
*/
var supports=function(){"use strict";/*global document*/
var a,b,c,d=document.createElement("div").style,e=["","Moz","Webkit","Khtml","O","ms"];return function(f){if("string"==typeof d[f])return!0;for(f=f.replace(/^[a-z]/,function(a){return a.toUpperCase()}),b=0,c=e.length;c>b;b+=1)if(a=e[b]+f,"string"==typeof d[a])return!0;return!1}}(),Slider=function(a){"use strict";/*global jQuery, setTimeout, clearTimeout*/
var b={npos:0,timer:null,config:function(a){b.target=a.target,b.container=b.target.find(".slider-wrapper"),b.sWidth=b.container.find(".slide").outerWidth(!0),b.max=b.container.find(".slide").length,b.tWidth=b.sWidth*b.max,b.time=a.time||5e3},early:function(){var b,c,d=this,e=d.target;for(d.container.css({width:d.tWidth}),e.append(d.pager()),b=0,c=d.max;c>b;b+=1)d.items(b+1).insertBefore(a(".slider-nav .next").parents("li"));e.find(".bullet:first").addClass("active")},events:function(){var c=this,d=c.target;d.on("click",".slider-nav a",function(b){b.preventDefault();var d=a(this),e=d.html();d.hasClass("next")&&c.next(),d.hasClass("prev")&&c.prev(),d.hasClass("bullet")&&(c.bullets(e),c.update())}),c.container.on({mouseenter:function(){clearTimeout(c.timer)},mouseleave:function(){b.auto()}})},slip:function(){supports("transition")?b.container.css({left:-b.npos*b.sWidth}):b.container.animate({left:-b.npos*b.sWidth},800)},bullets:function(a){clearTimeout(b.timer),b.auto(),b.npos=parseInt(a,null)-1,b.slip()},prev:function(){clearTimeout(b.timer),b.auto(),b.npos-=1,b.npos<0&&(b.npos=b.max-1),b.slip(),b.update()},next:function(){clearTimeout(b.timer),b.auto(),b.npos+=1,b.npos>b.max-1&&(b.npos=0),b.slip(),b.update()},update:function(){var a=this,b=a.target;b.find(".bullet").removeClass("active"),b.find(".bullet").eq(a.npos).addClass("active")},auto:function(){var a=this;a.timer=setTimeout(a.next,a.time)},pager:function(){var b=a('<ul class="slider-nav"><li><a href="#" class="control prev">Prev</a></li><li><a href="#" class="control next">Next</a></li></ul>');return b},items:function(b){var c=a('<li><a class="bullet" href="#">'+b+"</a></li>");return c},init:function(a){b.config(a),b.max&&1!==b.max&&(b.auto(),b.events(),b.early())}};return{init:b.init}}(jQuery);