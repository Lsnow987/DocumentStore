package edu.yu.cs.com1320.project.stage5.impl;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import edu.yu.cs.com1320.project.stage5.Document;
import edu.yu.cs.com1320.project.stage5.PersistenceManager;

//import javax.xml.bind.DatatypeConverter;
//import javax.xml.bind.DatatypeConverter;
import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class  DocumentPersistanceManager implements PersistenceManager<URI, Document> {
    private DocSerializer docSerializer = new DocSerializer();
    private class DocSerializer implements JsonSerializer<Document> {
        @Override
        public JsonElement serialize(Document document, Type type, JsonSerializationContext jsonSerializationContext) {
            JsonObject ob = new JsonObject();
            if (document.getDocumentTxt() != null) {
                //////System.out.println("text ADDDEEDEDEDEDEDE" + document.getDocumentTxt());
                ob.addProperty("text", document.getDocumentTxt());
            } else {
                //DatatypeConverter.setDatatypeConverter(byte);
                String base64Encoded = DatatypeConverter.printBase64Binary(document.getDocumentBinaryData());
                ob.addProperty("binaryData", base64Encoded);

                //Base64.Decoder decoder = Base64.getDecoder();
                //byte[] bytes = decoder.decode(encodedString);


                //Base64.Encoder encoder = Base64.getEncoder();

                //String encodedString = encoder.encodeToString(originalString.getBytes());

                ////System.out.println(encodedString);
            }
            ob.addProperty("uri", document.getKey().toString());
            Gson gson = new Gson();
            Type gsonType = new TypeToken<HashMap>() {}.getType();
            String gsonString = gson.toJson(document.getWordMap(), gsonType);
            ob.addProperty("word map", gsonString);
            //////System.out.println(ob + "OOOOOOOOOOBBBBBBBBBB");
            return ob;
        }

    }
    private class DocDeserializer implements JsonDeserializer<Document> {
        @Override
        public Document deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            JsonObject jObject = jsonElement.getAsJsonObject();
            Gson gson = new Gson();
            Map wordMap = gson.fromJson(jObject.get("word map").getAsString(), Map.class);
            JsonElement jsonStringTextOrBinaryData;
            String jStringText = "";
            byte[] base64Decoded = null;
            try {
                jsonStringTextOrBinaryData = jObject.get("text");
                jStringText = jsonStringTextOrBinaryData.getAsString();
            }catch(Exception e){
                jsonStringTextOrBinaryData = jObject.get("binaryData");
                String byteString = jsonStringTextOrBinaryData.getAsString();
                base64Decoded = DatatypeConverter.parseBase64Binary(byteString);
            }
            URI u = null;
            try {
                u = new URI(jObject.get("uri").getAsString());
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            if(base64Decoded == null) {
                return new DocumentImpl(u, jStringText, wordMap);
            }else{
                return new DocumentImpl(u, base64Decoded, wordMap);
            }
        }
    }

    private File baseDir;
    private  HashSet<URI> set=new HashSet();

    public DocumentPersistanceManager(File baseDir) {
        if (baseDir != null) {
            this.baseDir = baseDir;
        }else {
            this.baseDir = new File(System.getProperty("user.dir"));
        }
    }

    @Override
    public void serialize(URI u, Document doc) throws IOException {
        if(u== null || doc == null){
            throw new IllegalArgumentException("arguments are null");
        }
        //System.out.println(u + "    in SERIALIZE in document persistance manager");
        /*StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

        //Once you get StackTraceElement you can also print it to console
        System.err.println("displaying Stack trace from StackTraceElement in Java");
        for(StackTraceElement st : stackTrace){
            System.err.println(st);
        }*/
        set.add(u);
        String uriPath = File.separator + u.getPath();
        String domain = "";
        if(u.getHost() != null) {
            domain = File.separator + u.getHost();
        }

        //if(uriPath.endsWith(File.separator)){
            //uriPath = uriPath.substring(0, uriPath.length() - 1);
        //}
        uriPath += ".json";
        if (uriPath.equals(File.separator + ".json")){
            uriPath = ".json";
        }
        //System.out.println(uriPath + " uri .json addition");
        String baseDirPath = this.baseDir.getAbsolutePath();
        String filePath = baseDirPath + domain + uriPath;
        //System.out.println(uriPath + "   uriPath serialize");
        //System.out.println(domain + "     domain serialize");
        //System.out.println(baseDirPath + "   basedirpath serialize");
        //System.out.println(filePath + "     filepath serialize");
        ////System.out.println(filePath + "filepath");
        //////System.out.println(filePath + "filepatheeeeeeee");
        File fileDirectory = new File(filePath); //creating new files to access the old - need make list?
        ////System.out.println(fileDirectory.toString());
        //File fileDirectory = new File(filePath.substring(0,filePath.lastIndexOf("/")));

        fileDirectory.mkdirs();
        //////System.out.println(fileDirectory.toString());
        fileDirectory.delete();
        //////System.out.println(fileDirectory.toString());
        ////System.out.println(fileDirectory.toString() + "    to string serialize");

        BufferedWriter writer = new BufferedWriter(
                new FileWriter(fileDirectory));


        //GsonBuilder gsonBuilder = new GsonBuilder();
        //gsonBuilder.registerTypeAdapter(Document.class, docSerializer);
        Gson gson = new Gson();//= gsonBuilder.create(); //need this???
        String jsonString = gson.toJson(docSerializer.serialize(doc, Document.class, null));
        writer.write(jsonString);
        writer.close();
        //byte []b = Files.readAllBytes(Path.of(String.valueOf(fileDirectory)));
        ////System.out.println(new String(b) + "read all bytes???");
    }
    private boolean deleteFile(File domainFile, File actualFile){
        //System.out.println("why am i deleting the file");
        /*StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        //Once you get StackTraceElement you can also print it to console
        System.err.println("displaying Stack trace from StackTraceElement in Java");
        for(StackTraceElement st : stackTrace){
            System.err.println(st +"    " +actualFile.getAbsolutePath().toString());
        }*/
        File deletedFile = actualFile;
        boolean x = false;
        while(!deletedFile.equals(domainFile)){
            ////System.out.println(actualFile.equals(domainFile) + "   truetrue");
            File parentFile = deletedFile.getParentFile();
            ////System.out.println(deletedFile.getAbsolutePath().toString() + "      11111absolute path");

            if(deletedFile.delete()){
                x = true;
                //System.out.println("in dekleted file");
            }else{
                //System.out.println("reallyin dekleted file");
            }
            ////System.out.println(deletedFile.getAbsolutePath().toString() + "     absolute path");
            deletedFile = parentFile;
        }
        domainFile.delete();
        return x;
        /*File[] files = domainFile.listFiles();
        //assert files != null;
        for (File file : files)
        {
            ////System.out.println(file + "   this shouldnt have been deleted");
           if (file.getAbsolutePath().toString().endsWith(".json")){
               if (file.equals(actualFile)) {
                   //System.out.println(actualFile + "    actual file");
                   file.delete();
               }
           }
            //if (!file.delete()){
           deleteFile(file, actualFile);
           ////System.out.println(file + "   2222222this shouldnt have been deleted");
           //System.out.println("file before deletion");
            file.delete();
            //}
        }
        return domainFile.delete();*/
    }

    @Override
    public Document deserialize(URI u) throws IOException { //swithcing ///??????
        if(u== null){
            throw new IllegalArgumentException("arguments are null");
        }
        if(!set.contains(u)){
            throw new IllegalArgumentException("never serialized");
        }
        //System.out.println(u + "    in deserialize in document persistance manager");
       /* StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

        //Once you get StackTraceElement you can also print it to console
        System.err.println("displaying Stack trace from StackTraceElement in Java");
        for(StackTraceElement st : stackTrace){
            System.err.println(st);
        }*/
        //String uri = u.toString();
        ////System.out.println(u.toString());
        String uriPath = File.separator + u.getPath();
        String domain = "";
        if (u.getHost() != null) {
            domain = File.separator + u.getHost();
        }
        ////System.out.println(domain + "    domain");
        //uri = uri.replace( "http:/", "");
        uriPath += ".json";
        if (uriPath.equals(File.separator +".json")){
            uriPath = ".json";
        }
        //////System.out.println(uriPath + " uri .json addition");
        String baseDirPath = this.baseDir.getAbsolutePath();
        String filePath = baseDirPath + domain + uriPath;
        ////System.out.println(filePath + "filepatheeeeeeee");
        ////System.out.println(filePath + "filepath");
        File fileDirectory = new File(filePath); //creating new files to access the old - need make list?
        ////System.out.println(fileDirectory.toString());
        //fileDirectory.mkdirs();
        //fileDirectory.delete();

        ////System.out.println(baseDirPath + "baseDirPath");
        ////System.out.println(domain + "domain without.json");
        File domainFile = new File((baseDirPath + domain));
        //byte[] b = Files.readAllBytes(Path.of(String.valueOf(this.baseDir) + "\\edu.yu.cs"));
        byte[] b = null;
        try {
            ////System.out.println("about to read bytes in deserialize");
            ////System.out.println(String.valueOf(fileDirectory));
            b = Files.readAllBytes(Path.of(String.valueOf(fileDirectory)));
        }catch (IOException e){
            ////System.out.println(u + "    in deeeeeserializeexception");
            throw new IllegalArgumentException("doc not there");
        }
        String s = new String(b);
        ////System.out.println(s + "    this is ssssssssssssssss");
        /*boolean x = false;
        //////System.out.println("hiiiiiiiiiiiiiiiihello lawrence!!!!!!!!!!!");
        while(!x) {
            //////System.out.println("hiiiiiiiiiiiiiiiihello lawrence!!!!!!!!!!!");
            try {
                Files.delete(Path.of((baseDirPath + "\\" + domain)));
                x = true;
                //////System.out.println("hiiiiiiiiiiiiiiiihello lawrence!!!!!!!!!!!");
            } catch (Exception e) {
                fileDirectory.delete();
                //////System.out.println("hello lawrence!!!!!!!!!!!");
                x = false;
            }
        }*/
        //Scanner scanner = new Scanner(fileDirectory);
        //String j = new String();
        /*while(scanner.hasNext()){
            j += scanner.next();
            //////System.out.println(j + "hi yair ");
        }*/
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(DocumentImpl.class, new DocDeserializer())
                .create();
        Document doc = gson.fromJson(s, DocumentImpl.class);
        //////System.out.println(s + "SSSSSSSSSSSSSSS");
        ////System.out.println(domainFile.toString());
        this.delete(u);
       /* if (u.getHost() != null && !u.getPath().equals("")){
            ////System.out.println(u.getHost() + "  hosthost  " + u.getPath() + "  path");
            //System.out.println("akiva cooper");
            deleteFile(domainFile, fileDirectory);
        }else{
            //System.out.println("2.0akiva cooper");
            domainFile = new File((baseDirPath + domain + ".json"));
            ////System.out.println(domainFile.toString() + "      domain file to string");
            domainFile.delete();
        }*/
        //Gson gson = new Gson();
        //JsonElement jsonElement = gson.toJson(j);
        //array = new char[Integer.MAX_VALUE];
        //FileReader fileReader = new FileReader(fileDirectory);
        //fileReader.read(array);
        return doc;
    }

    @Override
    public boolean delete(URI u) throws IOException {
        if(u== null){
            throw new IllegalArgumentException("arguments are null");
        }
        if(!set.contains(u)){
            return false;
        }
        //System.out.println(u + "    in DELETE in document persistance manager");
        String uriPath = File.separator + u.getPath();
        String domain = "";
        if (u.getHost() != null) {
            domain = File.separator + u.getHost();
        }
        uriPath += ".json";
        if (uriPath.equals(File.separator+ ".json")){
            uriPath = ".json";
        }
        String baseDirPath = this.baseDir.getAbsolutePath();
        String filePath = baseDirPath + domain + uriPath;
        File fileDirectory = new File(filePath); 
        File domainFile = new File((baseDirPath + domain));
        //System.out.println(uriPath + "   uriPath delete");
        //System.out.println(domain + "     domain delete");
        //System.out.println(baseDirPath + "   basedirpath delete");
        //System.out.println(filePath + "     filepath delete");
        if (u.getHost() != null && !u.getPath().equals("")){
            return deleteFile(domainFile, fileDirectory);
        }else if (u.getHost() != null){
            domainFile = new File((baseDirPath + domain + ".json"));
            return domainFile.delete();
        }else{
            domainFile = new File((baseDirPath + uriPath));
            return domainFile.delete();
        }


        /*
        String uriPath =u.getPath();
        String domain = "\\" + u.getHost();
        //uri = uri.replace( "http:/", "");
        uriPath += ".json";
        //////System.out.println(uriPath + " uri .json addition");
        String baseDirPath = this.baseDir.getAbsolutePath();
        String filePath = baseDirPath + domain + uriPath;
        //////System.out.println(filePath + "filepatheeeeeeee");
        File fileDirectory = new File(filePath); //creating new files to access the old - need make list?s
        fileDirectory.mkdirs();
        return fileDirectory.delete();*/
    }
}