package com.datwhite.passwordmanagertest.screens.main;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.datwhite.passwordmanagertest.App;
import com.datwhite.passwordmanagertest.R;
import com.datwhite.passwordmanagertest.auth.AuthActivity;
import com.datwhite.passwordmanagertest.dialog.CustomDialogFragment;
import com.datwhite.passwordmanagertest.model.Password;
import com.datwhite.passwordmanagertest.screens.details.PasswordAddActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import static com.datwhite.passwordmanagertest.crypto.AES.decrypt;
import static com.datwhite.passwordmanagertest.crypto.AES.getKeyFromPassword;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        recyclerView = findViewById(R.id.list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        //отрисовка разделителей между элементами
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        //Привязка viewmodel к view
        //(установка адаптера)
        final Adapter adapter = new Adapter();
        recyclerView.setAdapter(adapter);

        //кнопка в правом нижнем углу
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PasswordAddActivity.start(MainActivity.this, null);
            }
        });



        //Подкючение Viewmodel
        MainViewModel mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        //"подписка" на livedata
        mainViewModel.getPasswordLiveData().observe(this, new Observer<List<Password>>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onChanged(List<Password> passwords) {
//                for (Password p : passwords) {
////                    System.out.println("Encrypted pass " + p.getText());
//
//                    try {
//                    String algorithm = "AES";
//                    String input = p.getText();
//                    String inputPassword = App.getGlobalPass();
//                    String salt = "GfH31Z5a";
//                    SecretKey key = getKeyFromPassword(inputPassword, salt);
//                    String decrypted = decrypt(algorithm, input, key);
//                    p.setText(decrypted);
//                    } catch (NoSuchAlgorithmException e) {
//                        e.printStackTrace();
//                    } catch (InvalidKeySpecException e) {
//                        e.printStackTrace();
//                    } catch (NoSuchPaddingException e) {
//                        e.printStackTrace();
//                    } catch (InvalidAlgorithmParameterException e) {
//                        e.printStackTrace();
//                    } catch (InvalidKeyException e) {
//                        e.printStackTrace();
//                    } catch (BadPaddingException e) {
//                        e.printStackTrace();
//                    } catch (IllegalBlockSizeException e) {
//                        e.printStackTrace();
//                    }
//
////                    System.out.println("Decrypted pass " + p.getText());
//                }

                adapter.setItems(passwords);
            }
        });
    }
}