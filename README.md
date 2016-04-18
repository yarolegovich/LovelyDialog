# LovelyDialog
This library is a set of simple wrapper classes that are aimed to help you easily create fancy material dialogs.

## Gradle 
Add this into your dependencies block.
```
compile 'com.yarolegovich:lovely-dialog:1.0.0'
```

## Wiki
Each dialog has colored top, icon, title and message + its own features. There are 6 types of dialogs available:
* [LovelyStandardDialog] (https://github.com/yarolegovich/lovely-dialog#LovelyStandardDialog)
* [LovelyInfoDialog] (https://github.com/yarolegovich/lovely-dialog#LovelyInfoDialog)
* [LovelyTextInputDialog] (https://github.com/yarolegovich/lovely-dialog#LovelyTextInputDialog)
* [LovelyChoiceDialog] (https://github.com/yarolegovich/lovely-dialog#LovelyChoiceDialog)
* [LovelyProgressDialog] (https://github.com/yarolegovich/lovely-dialog#LovelyProgressDialog)
* [LovelyCustomDialog] (https://github.com/yarolegovich/lovely-dialog#LovelyCustomDialog)

#### LovelyStandardDialog
You can set positive, negative and neutral button here. Listeners can be set individually for each button, one for all three or not set at all (onClick on any button dialog will be just dismissed).

````java
new LovelyStandardDialog(this)
      .setTopColor(color(R.color.indigo))
      .setButtonsColor(color(R.color.darkDeepOrange))
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

````
#### LovelyInfoDialog
Dialog for displaying information to the user, content is scrollable. There is an option to show Don't show again checkbox. If checked - dialog won't be called next time. This can be useful when showing some tutorials, for example. 
```java
new LovelyInfoDialog(this)
      .setTopColor(color(R.color.darkBlueGrey))
      .setIcon(R.drawable.ic_info_outline_white_36dp)
      //This will add Don't show again checkbox to the dialog. You can pass any ID as argument
      .setNotShowAgainOptionEnabled(0)
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
      .setTopColor(color(R.color.darkGreen))
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
      .setTopColor(color(R.color.darkRed))
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
      .setTopColor(color(R.color.darkDeepOrange))
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
      .setTopColor(color(R.color.teal))
      .show();
```
#### LovelyCustomDialog
Here you can pass your own view to be displayed. Title, message, color header and icon will still be available for use. You can provide configurators, click listeners and instance state save handlers.
```java
new LovelyCustomDialog(this)
      .setView(R.layout.item_donate_option)
      .setTopColor(color(R.color.darkDeepOrange))
      .setTitle(R.string.text_input_title)
      .setMessage(R.string.text_input_message)
      .setIcon(R.drawable.ic_assignment_white_36dp)
      .configureView(/* ... */)
      .setListener(R.id.ld_btn_yes, /* ... */)
      .setInstanceStateManager(/* ... */)
      .show();
```
