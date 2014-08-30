package com.taucarre.smartdeals.smartdealsapp.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.appspot.smart_deals.smartdeals.Smartdeals;

import com.appspot.smart_deals.smartdeals.model.Comment;
import com.google.common.base.Strings;
import com.taucarre.smartdeals.smartdealsapp.R;
import com.taucarre.smartdeals.smartdealsapp.application.AppConstants;
import com.taucarre.smartdeals.smartdealsapp.application.SmartDealsApplication;
import com.taucarre.smartdeals.smartdealsapp.persistence.CommentairesDataDao;
import com.taucarre.smartdeals.smartdealsapp.persistence.DbHelper;
import com.taucarre.smartdeals.smartdealsapp.services.UpdaterService;

import java.io.IOException;

public class ListeCommentairesActivity extends BaseActivity {

    private static final String TAG = ListeCommentairesActivity.class.getSimpleName();

    private static final String SEND_DEALS_NOTIFICATION =  "com.taucarre.smartdeals.SEND_DEALS_NOTIFICATIONS";

    Cursor cursor;
    ListView listComentaires;
    SimpleCursorAdapter adapter;
    static final String[] FROM = {DbHelper.CREE_A_COMMENTAIRE, DbHelper.NOM_USER_COMMENTAIRE,
            DbHelper.CONTENU_COMMENTAIRE};
    static final int[] TO = {R.id.commentaireAjouteA, R.id.nomUserCommentaire, R.id.texteCommentaire};
    Long idDeal;
    EditText commentaitreText;
    private CommentairesReceiver receiver;
    private IntentFilter filter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_commentaires);
        smartDealsApplication = (SmartDealsApplication) getApplication();
        idDeal = getIntent().getLongExtra("idDeal", new Long(0));

         Intent intent = new Intent("smartdeals.action.updatecommentaire");
         intent.putExtra("smartdeals.update.iddeal", idDeal);
         startService(intent);

        listComentaires = (ListView) findViewById(R.id.listCommentaires);

        receiver = new CommentairesReceiver();
        filter = new IntentFilter(UpdaterService.NEW_COMMENTS_INTENT);


    }

    @Override
    protected void onResume() {
        super.onResume();
        this.setupList();
        registerReceiver(receiver,filter, SEND_DEALS_NOTIFICATION, null);

    }

    @Override
    protected void onPause() {
        super.onPause();

        unregisterReceiver(receiver);

    }

    private void setupList() {
        CommentairesDataDao dao = new CommentairesDataDao(smartDealsApplication);
        cursor = dao.getListeCommentairesByDeal(idDeal);
        startManagingCursor(cursor);

        // Setup Adapter
        adapter = new SimpleCursorAdapter(this, R.layout.commentaire_item, cursor, FROM, TO);
        adapter.setViewBinder(VIEW_BINDER);
        listComentaires.setAdapter(adapter);
    }


    static final SimpleCursorAdapter.ViewBinder VIEW_BINDER = new SimpleCursorAdapter.ViewBinder() {

        public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
            if (view.getId() != R.id.commentaireAjouteA) return false;

            long timestamp = cursor.getLong(columnIndex);
            CharSequence relTime = DateUtils.getRelativeTimeSpanString(view.getContext(), timestamp);
            ((TextView) view).setText(relTime);

            return true;
        }

    };


    public void onClickAjouterCommentaire(View view) {
        View rootView = view.getRootView();

        commentaitreText = (EditText) rootView.findViewById(R.id.texteCommentaireInput);
        if (commentaitreText.getText() == null ||
                Strings.isNullOrEmpty(commentaitreText.getText().toString())) {
            Toast.makeText(this, "Input a commentaire", Toast.LENGTH_SHORT).show();
            return;
        }
        ;

        final String contenuCommentaire = commentaitreText.getText().toString();


        AsyncTask<Void, Void, Comment> sendCommentaire = new AsyncTask<Void, Void, Comment>() {
            @Override
            protected Comment doInBackground(Void... unused) {
                // Retrieve service handle.
                Smartdeals apiServiceHandle = AppConstants.getApiServiceHandle();

                try {
                    Comment comment = new Comment();
                    comment.setIdUser(smartDealsApplication.getUsersAuthentifie().get(0).getIdUser());
                    comment.setNomUser(smartDealsApplication.getUsersAuthentifie().get(0).getName());
                    comment.setTxtComment(contenuCommentaire);
                    comment.setIdDeal(idDeal);

                    Smartdeals.InsertComment insertCommentCommand = apiServiceHandle.insertComment(comment);
                    insertCommentCommand.execute();
                    return comment;
                } catch (IOException e) {
                    Log.e(TAG, "Exception during API call", e);
                }
                return null;
            }

            @Override
            protected void onPostExecute(Comment comment) {
                if (comment != null) {
                    Toast.makeText(getBaseContext(), "Comment enregistré", Toast.LENGTH_LONG).show();
                } else {
                    Log.e(TAG, "No Deals were returned by the API.");
                }
            }
        };

        sendCommentaire.execute((Void) null);
    }

    class CommentairesReceiver  extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            setupList();
            Log.d(TAG, "receiving new comments");
        }
    }
}


