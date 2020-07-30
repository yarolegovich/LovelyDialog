# LovelyDialog
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-LovelyDialog-green.svg?style=true)](https://android-arsenal.com/details/1/3452)

This library is a set of simple wrapper classes that are aimed to help you easily create fancy material dialogs.

![Screenshots](https://raw.githubusercontent.com/yarolegovich/lovelydialog/master/art/lovelydialogs_framed.png)

## Gradle 
Add this into your dependencies block.
```
compile 'com.yarolegovich:lovely-dialog:1.1.1'
```

## Wiki
### General
As advised in Effective Java
>  Favor composition over inheritance.

LovelyDialog doesn't subclass any Dialog related classes, it is just a lightweight extensible wrapper for Dialog and manipulations with custom view. If you would like to improve something - pull requests are appreciated.

Sample project that shows how to work with different dialogs and handle screen rotation is available under the [sample module](https://github.com/yarolegovich/LovelyDialog/tree/master/sample).
### Dialog types
Each dialog has colored top, icon, title and message + its own features. There are 6 types of dialogs available:
* [LovelyStandardDialog](#lovelystandarddialog)
* [LovelyInfoDialog](#lovelyinfodialog)
* [LovelyTextInputDialog](#lovelytextinputdialog)
* [LovelyChoiceDialog](#lovelychoicedialog)
* [LovelyProgressDialog](#lovelyprogressdialog)
* [LovelyCustomDialog](#lovelycustomdialog)

### Access to inner View objects

Starting from a version 1.1.0 of the library, you have an access to dialog's inner `View` objects via methods whose names are prefixed with `configure`. For example:

```java
lovelyDialog
      .configureView(rootView -> /* you can find any view here, view ids are prefixed with ld_ */)
      .configureTitleView(title -> title.setTextSize(customTextSize))
      .configureMessageView(message -> message.getPaint().setShader(customShader))
      .show();
```
I advise not to overuse this feature. If you are doing it, think of creating a custom Dialog subclass. `LovelyTextInpuDialog` exposes its `EditText` via `configureEditText`.

#### LovelyStandardDialog
You can set positive, negative and neutral button here. Listeners can be set individually for each button, one for all three or not set at all (onClick on any button dialog will be just dismissed).

```java
new LovelyStandardDialog(this, LovelyStandardDialog.ButtonLayout.VERTICAL)
      .setTopColorRes(R.color.indigo)
      .setButtonsColorRes(R.color.darkDeepOrange)
      .setIcon(R.drawable.ic_star_border_white_36dp)
      .setTitle(R.string.rate_title) 
      .setMessage(R.string.rate_message)
      .setPositiveButton(android.R.string.ok, new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Toast.makeText(context, "positive clicked", Toast.LENGTH_SHORT).show();
          }
      }) 
      .setNegativeButton(android.R.string.no, null)
      .show();

```
#### LovelyInfoDialog
Dialog for displaying information to the user, content is scrollable. There is an option to show Don't show again checkbox. If checked - dialog won't be called next time. This can be useful when showing some tutorials, for example. 
```java
new LovelyInfoDialog(this)
      .setTopColorRes(R.color.darkBlueGrey)
      .setIcon(R.drawable.ic_info_outline_white_36dp)
      //This will add Don't show again checkbox to the dialog. You can pass any ID as argument
      .setNotShowAgainOptionEnabled(0)
      .setNotShowAgainOptionChecked(true)
      .setTitle(R.string.info_title)
      .setMessage(R.string.info_message)
      .show();
```
#### LovelyChoiceDialog
Here you can use either single choice or multi choice dialogs. In case of multi choice dialog - Confirm button will appear. You can pass items as array, List (.toString() will be used to display them as simple text items) or provide your custom adapter.

##### Single choice
```java
ArrayAdapter<DonationOption> adapter = new DonationAdapter(this, loadDonationOptions());
new LovelyChoiceDialog(this)
      .setTopColorRes(R.color.darkGreen)
      .setTitle(R.string.donate_title)
      .setIcon(R.drawable.ic_local_atm_white_36dp)
      .setMessage(R.string.donate_message)
      .setItems(adapter, new LovelyChoiceDialog.OnItemSelectedListener<DonationOption>() {
           @Override
           public void onItemSelected(int position, DonationOption item) {
               Toast.makeText(context, getString(R.string.you_donated, item.amount),Toast.LENGTH_SHORT).show();
           }
      })
      .show();
```
##### Multi choice
```java
String[] items = getResources().getStringArray(R.array.food);
new LovelyChoiceDialog(this, R.style.CheckBoxTintTheme)
      .setTopColorRes(R.color.darkRed)
      .setTitle(R.string.order_food_title)
      .setIcon(R.drawable.ic_food_white_36dp)
      .setItemsMultiChoice(items, new LovelyChoiceDialog.OnItemsSelectedListener<String>() {
          @Override
          public void onItemsSelected(List<Integer> positions, List<String> items) {
              Toast.makeText(MainActivity.this,
                      getString(R.string.you_ordered, TextUtils.join("\n", items)),
                      Toast.LENGTH_SHORT)
                      .show();
          }
      })
      .setConfirmButtonText(R.string.confirm)
      .show();
```
#### LovelyTextInputDialog
Dialog with EditText and Confirm button. You can set TextFilter object to specify acceptable input. 
```java
new LovelyTextInputDialog(this, R.style.EditTextTintTheme)
      .setTopColorRes(R.color.darkDeepOrange)
      .setTitle(R.string.text_input_title)
      .setMessage(R.string.text_input_message)
      .setIcon(R.drawable.ic_assignment_white_36dp) 
      .setInputFilter(R.string.text_input_error_message, new LovelyTextInputDialog.TextFilter() {
          @Override
          public boolean check(String text) {
              return text.matches("\\w+");
          }
      })
      .setConfirmButton(android.R.string.ok, new LovelyTextInputDialog.OnTextInputConfirmListener() {
           @Override
           public void onTextInputConfirmed(String text) {
              Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
           }
      }) 
      .show();
```
#### LovelyProgressDialog
Dialog with standard Android ProgressBar. Not cancelable by default.
```java
new LovelyProgressDialog(this)
      .setIcon(R.drawable.ic_cast_connected_white_36dp)
      .setTitle(R.string.connecting_to_server)
      .setTopColorRes(R.color.teal)
      .show();
```
#### LovelyCustomDialog
Here you can pass your own view to be displayed. Title, message, color header and icon will still be available for use. You can provide configurators, click listeners and instance state save handlers.
```java
new LovelyCustomDialog(this)
      .setView(R.layout.item_donate_option)
      .setTopColorRes(R.color.darkDeepOrange)
      .setTitle(R.string.text_input_title)
      .setMessage(R.string.text_input_message)
      .setIcon(R.drawable.ic_assignment_white_36dp)
      .configureView(/* ... */)
      .setListener(R.id.ld_btn_yes, /* ... */)
      .setInstanceStateManager(/* ... */)
      .show();
```
### Configuration changes
There is a class LovelySaveStateHandler that helps you to persist information about which dialog was shown (if any) between configuration changes. 
Each dialog (except LovelyCustomDialog) knows how to save and restore its state. 
Refer to [sample project](https://github.com/yarolegovich/LovelyDialog/blob/master/sample/src/main/java/com/yarolegovich/sample/MainActivity.java) for examples of how to deal with configuration changes.
### Tinting controls
If you want CheckBoxes, EditTexts etc. to be of different color - what you need is to define theme in xml
```xml
<style name="TintTheme" parent="Theme.AppCompat.Light.Dialog.Alert">
      <item name="colorAccent">@color/colorToTintWith</item>
</style>
```
and pass it as a second argument to dialog's constructor
```java
new LovelyTextInputDialog(this, R.style.TintTheme)
```
### Standard dialogs compatibility
If you don't want to rewrite your
```java
Dialog.OnClickListener
```
implementations, you can simply use
```java
LovalyDialogCompat.wrap(yourImplementation)
```
to pass it to one of the
```java
.setPositiveButton(...)
.setNegativeButton(...)
```
or the like.
### License
```
Copyright 2016 Yaroslav Shevchuk

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
