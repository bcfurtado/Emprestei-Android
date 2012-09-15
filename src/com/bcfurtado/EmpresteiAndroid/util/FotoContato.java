package com.bcfurtado.EmpresteiAndroid.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;

public class FotoContato {

	public static Bitmap pegar_foto( ContentResolver content , String nomeContato ){
		
		
		Cursor contatos = content.query(ContactsContract.Contacts.CONTENT_URI, new String[]{ 
				ContactsContract.Contacts._ID,
				ContactsContract.Contacts.DISPLAY_NAME,
				ContactsContract.Contacts.PHOTO_ID }, ContactsContract.Contacts.DISPLAY_NAME +"='"+ nomeContato + "'" , null, null);
		
		if ( contatos != null && contatos.moveToFirst() ) {
		
			int idContato = contatos.getInt( contatos.getColumnIndex(ContactsContract.Contacts._ID) );
			contatos.close();
			
			Uri contactUri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, idContato);
			Uri photoUri = Uri.withAppendedPath(contactUri, ContactsContract.Contacts.Photo.CONTENT_DIRECTORY);
			
			try {
				InputStream input = content.openInputStream( photoUri);
				Bitmap bm = BitmapFactory.decodeStream( input );
				input.close();
				return bm;
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
			
		} else {
			return null;
		}
	}
	
}
