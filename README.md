Smartface Calendar Plugin Android
===================

Smartface Calendar Plugin for Android is one of the first plugins of Smartface Plugin Project. This project is based on Caldroid project. It provides month based view. It can be used as a view or as a dialog. It suppots swipe to change month and full localization. Calendar plugin works with Android API 16 and above.

<img src="http://imgur.com/ifJonYx.png">

Setup
=====

To start using Calendar plugin you need to follow below steps:

 - Download plugin file.
 - Copy plugin file to Smartface plugin folder.
 - Select Calendar plugin in Smartface project settings.

To use Calendar with auto complate support (Optional)

 - Copy Calendar auto complate Javascript file to your project folder.
 - Load auto complate file in global.

You can look following guides to understand how can you pass these steps:

 - Adding a plugin to Smartface projects
 - Adding auto complate support for plugins

Features
========

###Can be embedded or shown as dialog

You can show SmartfaceCalendar as a dialogin your activity with below code:

``` Javascript
var calendar= new SmartfaceCalendar("Landroid/app/Activity;");
calendar.showAsDialog();
```

You can also embed caldroid fragment as a child in your application.

``` Javascript
var calendar= new SmartfaceCalendar("Landroid/app/Activity;");
Pages.Page1.add(calendar);
```

###Can change layout parameters of calendar dynamically

You can pass layout parameters with constructor

``` Javascript
var calendar= new SmartfaceCalendar(
	"Landroid/app/Activity;", 
	1000, 1000, 0, 0);
```

or you can use default size. In this case Calendar will use `MATCH_PARENT` for width, `WRAP_CONTEN` for height and `0` as top and left parameters.

``` Javascript
var calendar= new SmartfaceCalendar("Landroid/app/Activity;");
```

###Can change previous and next month button images

You should copy the images you want to use under `\resources\Images\android` folder in your project. You sould provide required images for every screen dpi supported. You do not need to use file extansions. Give only file name itself.

You should call `setNextImage` and `setPreviousImage` before adding calendar view or showing calendar dialog.

``` Javascript
calendar.setNextImage("right");
calendar.setPreviousImage("left");
```

###Can anable or disable swipe to change month

``` Javascript
calendar.setSwipeEnabled(false);
```
