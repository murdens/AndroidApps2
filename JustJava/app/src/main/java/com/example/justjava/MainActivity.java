/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/
package com.example.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * This app displays an order form to order coffee.
 * image from https://www.foodiesfeed.com/ adjusted in GIMP.
 */

public class MainActivity extends AppCompatActivity {
    /**
     * Set global variable for quantity.
     */
    int quantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the increment button is clicked.
     */
    public void increment(View view) {
        if (quantity == 20){
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the decrement button is clicked.
     */
    public void decrement(View view) {
        if (quantity == 0){
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the order button is clicked.
     */
   public void submitOrder(View view) {

        EditText enteredName = findViewById(R.id.name);
        String nameField = enteredName.getText().toString();

        CheckBox creamCheckBox = findViewById(R.id.cream_checkBox);
        boolean hasCream = creamCheckBox.isChecked();

        CheckBox chocCheckBox = findViewById(R.id.choc_checkBox);
        boolean hasChoc = chocCheckBox.isChecked();

        int price = calculatePrice(hasCream, hasChoc);
        String priceMessage = createOrderSummary(nameField, price, hasCream, hasChoc);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for "+nameField);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

         /**
     * Calculates the price of the order based on the current quantity.
     * @return the total price
      * @param addCream for checkbox
      * @param addChoc for checkbox
     * */
    private int calculatePrice(boolean addCream, boolean addChoc) {
        // Set price for 1 cup of coffee
        int basePrice = 5;
        // if user wants cream or choc, add to price
        if(addCream){
            basePrice += 2;
        }

        if(addChoc){
            basePrice += 1;
        }
        // calculate total price
        return quantity * basePrice;
    }

    /**
     * Creates an order summary to display on screen
     * @return text summary
     * @param price is the total order $
     * @param hasCream boolean to confirm if checkbox for cream on
     * @param hasChoc boolean to confirm if checkbox for choc on
     * */
    private String createOrderSummary(String nameField, int price, boolean hasCream, boolean hasChoc) {
        String priceMessage = "Name: "+ nameField;
        priceMessage += "\nQuantity: "+ quantity;
        priceMessage += "\nWhipped Cream? "+ hasCream;
        priceMessage += "\nChocolate? "+ hasChoc;
        priceMessage += "\nTotal: $" + price;
        priceMessage +=  "\nThank you!";
        return priceMessage;
    }

}