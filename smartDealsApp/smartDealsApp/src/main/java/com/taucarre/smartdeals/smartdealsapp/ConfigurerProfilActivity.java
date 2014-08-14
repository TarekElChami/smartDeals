package com.taucarre.smartdeals.smartdealsapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.appspot.smart_deals.smartdeals.Smartdeals;
import com.appspot.smart_deals.smartdeals.model.User;
import com.google.common.base.Strings;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ConfigurerProfilActivity extends ActionBarActivity {

    private static final String TAG = ConfigurerProfilActivity.class.getSimpleName();

    private final static int SELECT_PHOTO = 100;

    private ImageView avatarImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configurer_profil);
        avatarImage = (ImageView) findViewById(R.id.userAvatar);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.configurer_profil, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    public void onClickChooseAvatar(View view){
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent,SELECT_PHOTO);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(SELECT_PHOTO == requestCode){
            if(resultCode == RESULT_OK){
                Uri selectedImage = data.getData();
                try {
                    Bitmap bmpImage = decodeUri(selectedImage);
                    avatarImage.setImageBitmap(bmpImage);
                    avatarImage.setAdjustViewBounds(true);
                    avatarImage.setMaxHeight(128);
                    avatarImage.setMaxWidth(128);
                    Toast.makeText(getBaseContext(), "Image loaded", Toast.LENGTH_LONG).show();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }


            }
        }
    }

    private Bitmap decodeUri(Uri selectedImage) throws FileNotFoundException {

        // Decode image size
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage), null, o);

        // The new size we want to scale to
        final int REQUIRED_SIZE = 128;

        // Find the correct scale value. It should be the power of 2.
        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;
        while (true) {
            if (width_tmp / 2 < REQUIRED_SIZE
                    || height_tmp / 2 < REQUIRED_SIZE) {
                break;
            }
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }

        // Decode with inSampleSize
        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        return BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage), null, o2);

    }

    public void onClickInsertUser(View view){

        View rootView = view.getRootView();

        TextView userNameInput = (TextView) rootView.findViewById(R.id.userName);
        TextView userLoginInput = (TextView) rootView.findViewById(R.id.userLogin);
        TextView userPasswordInput = (TextView) rootView.findViewById(R.id.userPassword);
        TextView userMailInput = (TextView) rootView.findViewById(R.id.userMail);


        if (userNameInput.getText()==null ||
                Strings.isNullOrEmpty(userNameInput.getText().toString())) {
            Toast.makeText(this, "Please Input a User Name", Toast.LENGTH_SHORT).show();
            return;
        };
        if (userLoginInput.getText()==null ||
                Strings.isNullOrEmpty(userLoginInput.getText().toString())) {
            Toast.makeText(this, "Please Input a User Login", Toast.LENGTH_SHORT).show();
            return;
        };
        if (userPasswordInput.getText()==null ||
                Strings.isNullOrEmpty(userPasswordInput.getText().toString())) {
            Toast.makeText(this, "Please Input a User Password", Toast.LENGTH_SHORT).show();
            return;
        };
        if (userMailInput.getText()==null ||
                Strings.isNullOrEmpty(userMailInput.getText().toString())) {
            Toast.makeText(this, "Plese Input a User mail", Toast.LENGTH_SHORT).show();
            return;
        };

        Bitmap userAvatar = ((BitmapDrawable)avatarImage.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        userAvatar.compress(Bitmap.CompressFormat.PNG,100, stream);
        byte[] userAvatarByteArray = stream.toByteArray();
        try {
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        final String encodedAvatarImage = Base64.encodeToString(userAvatarByteArray,Base64.DEFAULT);

        final String userNameString = userNameInput.getText().toString();
        final String userLoginString = userLoginInput.getText().toString();
        final String userPasswordString = userPasswordInput.getText().toString();
        final String userMailString = userMailInput.getText().toString();


        AsyncTask<Void, Void, User> sendUser = new AsyncTask<Void, Void, User>() {
            @Override
            protected User doInBackground(Void... params) {
                Smartdeals apiServiceHandle = AppConstants.getApiServiceHandle();
            try {
                User user = new User();
                user.setBanned(false);
                user.setLogin(userLoginString);
                user.setMail(userMailString);
                user.setName(userNameString);
                user.setPassword(userPasswordString);
                user.setNotoriorite(0);
                user.setRoleUtilisateur("INSIDER");
                user.setAvatar(encodedAvatarImage);

                Smartdeals.InsertUser insertUserCommand = apiServiceHandle.insertUser(user);

                insertUserCommand.execute();
                return user;

                } catch (IOException e) {
                    Log.e(TAG, "Exception during API call", e);
                }

                return null;
            }

            @Override
            protected void onPostExecute(User user) {
                if (user !=null) {
                    Toast.makeText(getBaseContext(), "User enregistré", Toast.LENGTH_LONG).show();
                } else {
                    Log.e(TAG, "Failed to register new User.");
                }
            }
        };

        sendUser.execute((Void)null);

    }
}
