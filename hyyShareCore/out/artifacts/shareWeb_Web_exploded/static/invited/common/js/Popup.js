function PopupUtil() {
}

PopupUtil.bg = null;
PopupUtil.box = null;
PopupUtil.content = null;

PopupUtil.init = function () {
    PopupUtil.bg = document.createElement("div");
    PopupUtil.bg.id = "popup-bg";

    PopupUtil.box = document.createElement("div");
    PopupUtil.box.id = "popup-box";
    PopupUtil.bg.appendChild(PopupUtil.box);

    document.body.appendChild(PopupUtil.bg);

    $("#popup-bg").click(function (e) {
        if (e.target.id == "popup-bg") {
            PopupUtil.hide();
        }
    });
};
PopupUtil.initFrame = function () {
    PopupUtil.bg = document.createElement("div");
    PopupUtil.bg.id = "popup-bg";

    PopupUtil.box = document.createElement("div");
    PopupUtil.box.id = "popup-box";
    PopupUtil.bg.appendChild(PopupUtil.box);

    PopupUtil.content = document.createElement("iframe");
    PopupUtil.content.id = "popup-content";
    PopupUtil.box.appendChild(PopupUtil.content);

    document.body.appendChild(PopupUtil.bg);

    $("#popup-bg").click(function (e) {
        if (e.target.id == "popup-bg") {
            PopupUtil.hide();
        }
    });
};
/**
 * 显示popup
 * @param height 高度，-1则为90%，0则为内容的高度，0-1则为百分比，>1则为真是高度
 * @param width 宽度，-1或0则为95%，0-1则为百分比，>1则为真是宽度
 * @param content popup出的内容的html
 */
PopupUtil.show = function (width, height, content) {
    if ($("#popup-bg").length == 0) PopupUtil.init();

    document.body.style.overflow = "hidden";

    //PopupUtil.box.style.height = "0px";
    PopupUtil.bg.style.display = "block";
    PopupUtil.content = content;
    PopupUtil.box.innerHTML = "";
    PopupUtil.box.innerHTML = PopupUtil.content;
    PopupUtil.fixPopupSize(height, width);
};
PopupUtil.frameShow = function (top, height, url) {
    if ($("#popup-bg").length == 0) PopupUtil.initFrame();

    if (top > 0) {
        PopupUtil.box.style.marginTop = top + "px";
    }

    document.body.style.overflow = "hidden";

    PopupUtil.box.style.height = "0px";
    PopupUtil.bg.style.display = "block";
    PopupUtil.content.src = url;
    PopupUtil.content.onload = function () {
        PopupUtil.fixFramePopupHeight(height);
    };
};
PopupUtil.fixPopupSize = function (width, height) {
    var bgHeight = PopupUtil.bg.clientHeight * 0.90;
    var bgWidth = PopupUtil.bg.clientWidth * 0.95;

    var contentHeight = 0;
    var contentWidth = 0;
    if (height == -1) {
        contentHeight = bgHeight;
    }
    else if (height >= 1) {
        contentHeight = height;
    }
    else if (height > 0 && height < 1) {
        contentHeight = bgHeight * height;
    }
    else if (height == 0) {
        contentHeight = $(PopupUtil.box).height();
    }

    if (width >= 1) {
        contentWidth = width;
    }
    else if (width > 0 && width < 1) {
        contentWidth = bgWidth * width;
    }
    else {
        contentWidth = bgWidth;
    }

    if (contentHeight > bgHeight) contentHeight = bgHeight;
    if (contentWidth > bgWidth) contentWidth = bgWidth;

    var contentTop = (bgHeight / 0.9 - contentHeight) / 2;

    PopupUtil.box.style.height = contentHeight + "px";
    PopupUtil.box.style.width = contentWidth + "px";

    var padding = (bgWidth / 0.95 - contentWidth) / 2;

    PopupUtil.box.style.marginLeft = padding + "px";
    PopupUtil.box.style.marginTop = contentTop + "px";
};
PopupUtil.fixFramePopupHeight = function (height) {
    var contentHeight = 0;
    if (height > 0) {
        contentHeight = height;
    }
    else {
        contentHeight = PopupUtil.content.contentWindow.document.body.clientHeight;
    }
    var bgHeight = PopupUtil.bg.clientHeight * 0.95;
    if (contentHeight > bgHeight) contentHeight = bgHeight;
    PopupUtil.box.style.height = contentHeight + "px";
};
PopupUtil.hide = function () {
    PopupUtil.bg.style.display = "none";
    document.body.style.overflow = "scroll";
};