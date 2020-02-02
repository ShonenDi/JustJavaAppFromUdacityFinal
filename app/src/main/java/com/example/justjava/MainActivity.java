package com.example.justjava;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        int price;
        CheckBox checkWhippedCream = findViewById(R.id.whipped_cream_check);
        boolean checkCream = checkWhippedCream.isChecked();

        CheckBox checkChocolate = findViewById(R.id.chocolate_check);
        boolean checkChoco = checkChocolate.isChecked();

        if (checkCream && !checkChoco) {
             price = calculatePrice(5 + 1);
//            displayMessage(createOrderSummary(price, checkCream, checkChoco));
        } else if (!checkCream && checkChoco) {
             price = calculatePrice(5 + 2);
//            displayMessage(createOrderSummary(price, checkCream, checkChoco));
        } else if (checkChoco && checkCream) {
             price = calculatePrice(5 + 2 + 1);
//            displayMessage(createOrderSummary(price, checkCream, checkChoco));
        } else {
             price = calculatePrice(5);
//            displayMessage(createOrderSummary(price, checkCream, checkChoco));
        }
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order to " + addUserName());
        intent.putExtra(Intent.EXTRA_TEXT, createOrderSummary(price,checkCream,checkChoco));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }


    public String addUserName() {
        EditText userNameInput = findViewById(R.id.add_user_name);
        String userName = String.valueOf(userNameInput.getText());
        return userName;
    }

    /**
     * This's method calculate total price of order
     *
     * @return data type int, total price of order
     */
    private int calculatePrice(int pricePerCup) {
        return (quantity * pricePerCup);
    }

    /**
     * Give total order information to user
     *
     * @param totalPrice
     * @return String with information in order
     */


    private String createOrderSummary(int totalPrice, boolean addWhippedCream, boolean addChocolate) {
        String orderMassage = "Name: " + addUserName() + "\nAdd whipped cream? " + addWhippedCream + "\nAdd chocolate? " + addChocolate + "\nQuantity: " + quantity + "\nTotal: $" + totalPrice + "\nThank you!";
        return orderMassage;
    }

    /**
     * This method is called when the plus button is clicked.
     *
     * @return
     */
    public int increment(View view) {
        if (quantity == 100 && quantity >= 100) {
            Toast handerdCupsToast = Toast.makeText(getApplicationContext(), "you can not order more than 100 coffee", Toast.LENGTH_LONG);
            handerdCupsToast.show();
            return quantity = 100;
        } else {
            quantity = quantity + 1;
        }
        displayQuantity(quantity);
        return quantity;
    }

    /**
     * This method is called when the minus button is clicked.
     *
     * @return
     */
    public int decrement(View view) {
        if (quantity == 1 && quantity <= 1) {
            Toast zeroCupsToast = Toast.makeText(getApplicationContext(), "you can not order less than 1 coffee", Toast.LENGTH_LONG);
            zeroCupsToast.show();
            return quantity = 1;
        } else {
            quantity = quantity - 1;
        }
        displayQuantity(quantity);
        return quantity;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }
}