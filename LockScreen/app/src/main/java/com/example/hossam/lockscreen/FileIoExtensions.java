package com.example.hossam.lockscreen;

import android.content.Context;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.GregorianCalendar;

public  class FileIoExtensions
   {
       private Context context;
       public FileIoExtensions(Context context)
       {
           this.context=context;
       }


       //context method returns a string of files and directories in the wanted directory that supplied
       public String getDirectoriesAndFiles(String parent_directory_path)
       {
           File directory=new File(parent_directory_path);
           String[] directories_and_files = directory.list();
           StringBuilder stb=new StringBuilder();
           stb.append("\n Directories And Files In ("+parent_directory_path+") : ");
           if (directories_and_files!=null)
               for(String dd : directories_and_files)
               {
                   if((new File(parent_directory_path,dd)).isFile())
                       stb.append(" \n\n File : " + dd);
                   else
                       stb.append(" \n\n Directory : " + dd);
               }
           return  stb.toString();
       }


       //creating new  directory in the internal data directory
       //directory_to_create  the absolute path of the new directory
       //returns true if the directory created or exists else false
       public boolean createDirectoryInDataDirectory(String directory_to_create) {

           File directory;
           boolean directory_state = false;
           boolean directory_created = false;
           directory = new File(context.getFilesDir() + File.separator + directory_to_create);
           if(directory.exists()) {
               Toast.makeText(context, "directory exists : " + directory.getPath(), Toast.LENGTH_LONG).show();
               directory_state = true;
           }
           else{
               try {
                   directory_state = directory.mkdirs();
                   directory_created = true;
               }
               catch (Exception e) {
                   Toast.makeText(context, "error directory not created : " + e.getMessage(), Toast.LENGTH_LONG).show();
               }
           }
           if (directory_created&&directory_state) {
               Toast.makeText(context, "directory created : (" + directory.getPath()+")", Toast.LENGTH_LONG).show();
           }

           return directory_state;
       }



       //creating new  file in the internal data directory
       //directory_to_store_file_in  the absolute path of the  directory
       //return true if the file updated else false
       public boolean createNewFileInInternalDataDirectory(String directory_to_store_file_in,String new_file_name) {

           boolean file_state = false;
           File new_file = new File(context.getFilesDir() + File.separator + directory_to_store_file_in, new_file_name);

           if(new_file.getParentFile().exists())     //checking if the directory exists
               if(!new_file.exists()) {               //checking if the   file not exists then create new
                   try {
                       file_state = new_file.createNewFile();
                       // FileOutputStream ou = new FileOutputStream(new_file);
                       // ou.write("great".getBytes());
                   } catch (Exception e) {
                       Toast.makeText(context, "file not created : " + e.getMessage(), Toast.LENGTH_LONG).show();
                   }
               }
               else{
                   Toast.makeText(context, "file exists : " + new_file.getPath(), Toast.LENGTH_LONG).show();
               }
           else{
               Toast.makeText(context, "directory not exists : " + new_file.getParentFile(), Toast.LENGTH_LONG).show();
           }
           if (file_state) {
               Toast.makeText(context, "file created : " + new_file.getPath(), Toast.LENGTH_LONG).show();
           }
           return file_state;
       }

       //append text to existing file
       //if file not exists create and append text
       //if directory_in_the_internal_storage is null then passing the data folder
       public boolean appendTextToFileInInternalDataDirectory(String directory_in_the_internal_storage,String file_name,String text_to_append) {

           boolean file_state = false;
           File new_file;
           if( directory_in_the_internal_storage.equals(""))
               new_file = new File(context.getFilesDir(), file_name);
           else
               new_file = new File(context.getFilesDir() + File.separator + directory_in_the_internal_storage, file_name);

           if(new_file.getParentFile().exists())     //checking if the directory exists
               if(new_file.exists()) {               //checking if the   file not exists then create new
                   try {
                       byte[] text=(text_to_append).getBytes();
                       FileOutputStream ou = new FileOutputStream(new_file,true);
                       ou.write(text);
                       file_state=true;

                   } catch (Exception e) {
                       Toast.makeText(context, "file not updated : " + e.getMessage(), Toast.LENGTH_LONG).show();
                   }
               }
               else{
                   Toast.makeText(context, "file not exists : " + file_name, Toast.LENGTH_LONG).show();
               }

           else{
               Toast.makeText(context, "directory not exists : " + directory_in_the_internal_storage, Toast.LENGTH_LONG).show();

           }

           if (file_state) {
               Toast.makeText(context, "file updated : " + new_file.getPath(), Toast.LENGTH_LONG).show();
           }
           return file_state;
       }


       //add text to existing file from the file start
       //if file not exists create and add text
       //if directory_in_the_internal_storage is null then passing the data folder
       public boolean writeTextToFileInInternalDataDirectory(String directory_in_the_internal_storage,String file_name,String text_to_write) {

           boolean file_state = false;
           File new_file;
           if( directory_in_the_internal_storage.equals(""))
               new_file = new File(context.getFilesDir(), file_name);
           else
               new_file = new File(context.getFilesDir() + File.separator + directory_in_the_internal_storage,file_name);

           if(new_file.getParentFile().exists())     //checking if the directory exists
               if(new_file.exists()) {               //checking if the   file not exists then create new
                   try {
                       byte[] text=(text_to_write).getBytes();
                       FileOutputStream ou = new FileOutputStream(new_file);
                       ou.write(text);
                       file_state=true;

                   } catch (Exception e) {
                       Toast.makeText(context, "file not updated : " + e.getMessage(), Toast.LENGTH_LONG).show();
                   }
               }
               else{
                   Toast.makeText(context, "file not exists : " + file_name, Toast.LENGTH_LONG).show();
               }

           else{
               Toast.makeText(context, "directory not exists : " + directory_in_the_internal_storage, Toast.LENGTH_LONG).show();

           }

           if (file_state) {
               Toast.makeText(context, "file updated : " + new_file.getPath(), Toast.LENGTH_LONG).show();
           }
           return file_state;
       }


       //read the file entry into string variable
       //directory_in_internal_storage: the path to the directory that contains the file in the internal storage
       //not including the internal storage directory
       //file_to_read:the file to read
       //returns the text as a String
       public String readFileTextFromInternalStorage(String directory_in_internal_storage,String file_to_read)  {
           File new_file;
           StringBuilder text = new StringBuilder("");

           if( directory_in_internal_storage.equals(""))
               new_file = new File(context.getFilesDir(), file_to_read);
           else
               new_file = new File(context.getFilesDir() + File.separator + directory_in_internal_storage,file_to_read);

           if(new_file.exists()) {
               BufferedReader br;
               FileInputStream inputStream;
               try {
                   inputStream = new FileInputStream(new_file);
                   InputStreamReader isr = new InputStreamReader(inputStream);
                   br = new BufferedReader(isr);
                   String line;

                   while ((line = br.readLine()) != null) {
                       text.append(line);
                       text.append('\n');
                   }
                   br.close();
               }
               catch (IOException e) {
                   Toast.makeText(context, "error reading file : " + e.getMessage(), Toast.LENGTH_LONG).show();
               }
           }
           else{
               text.append("file not found");
           }
           return text.toString();
       }

       //returning string of the current time
       public  String getTime(){
           Calendar cal = new GregorianCalendar();
           int hours = cal.get(Calendar.HOUR);
           int minute = cal.get(Calendar.MINUTE);
           int second = cal.get(Calendar.SECOND);
           int ap = cal.get(Calendar.AM_PM);
           String amVSpm;
           if(ap == 0){
               amVSpm = "AM";
           }
           else{
               amVSpm = "PM";
           }

           return hours + "-" + minute + "-" + second + " " +amVSpm;
       }

   }
