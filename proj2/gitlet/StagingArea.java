package gitlet;

import java.io.File;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

import static gitlet.Utils.*;

public class StagingArea {
    public static final File ADDAREA = join(Repository.HELPERADD_DIR, "add_area");
    public static final File REMOVEAREA = join(Repository.HELPERADD_DIR, "remove_area");
    public static final File BLOBS_MAX = join(Repository.HELPERADD_DIR, "blobs_max");
    public static final File ADDSTATUS = join(Repository.HELPERADD_DIR, "add_status");
    public static final File MODSTATUS = join(Repository.HELPERADD_DIR, "modify_status");
    public static final File REMOVESTATUS = join(Repository.HELPERADD_DIR, "remove_status");
    public static final File ADDLIST = join(Repository.HELPERADD_DIR, "addInfo");
    public static final File MODLIST = join(Repository.HELPERADD_DIR, "modInfo");
    public static final File REMOVELIST = join(Repository.HELPERADD_DIR, "removeInfo");

    private  static HashMap<String,String>blobs=new HashMap<>();
    private  static HashMap<String,String>remove_blobs=new HashMap<>();
    private  static LinkedList<String>link_add=new LinkedList<>();
    private  static LinkedList<String>link_mod=new LinkedList<>();
    private  static LinkedList<String>link_remove=new LinkedList<>();
    private static int size=0;



    public static void init_Staging(){
        try{
            BLOBS_MAX.createNewFile();
            ADDAREA.createNewFile();
            ADDLIST.createNewFile();
            MODLIST.createNewFile();
            REMOVELIST.createNewFile();
            REMOVEAREA.createNewFile();
            writeObject(BLOBS_MAX,size);
            writeObject(ADDAREA,blobs);
            writeObject(ADDLIST,link_add);
            writeObject(MODLIST,link_mod);
            writeObject(REMOVELIST,link_remove);
            writeObject(REMOVEAREA,remove_blobs);
        }catch (IOException o){

        }
    }

    public static void add(String name,String hashcode){
        if(!ADDAREA.exists())
        {
            try{
                ADDAREA.createNewFile();

            }catch (IOException o){

            }
        }
        blobs=readObject(ADDAREA,HashMap.class);
        remove_blobs=readObject(REMOVEAREA,HashMap.class);
        int size_cur=readObject(BLOBS_MAX,Integer.class);

        link_remove=readObject(REMOVELIST,LinkedList.class);
        File addfile=new File(Repository.CWD,name);
        if(!addfile.exists()&&!link_remove.contains(name))
            Repository.exit("File does not exist.");

        if(link_remove.contains(name))
        {

            link_remove.remove(name);
            File f=new File(Repository.CWD,name);
            try{
                f.createNewFile();
                String con=remove_blobs.get(name);
                File file_r=new File(Repository.BLOBS_DIR,con);
                writeContents(f,readContents(file_r));
            }catch (IOException o){

            }
            remove_blobs.remove(name);

            writeObject(REMOVELIST,link_remove);
            writeContents(REMOVESTATUS,getNameList(link_remove));
            writeObject(REMOVEAREA,remove_blobs);
            return;
        }

        if(checkFileWithCWCommit(name,hashcode))

        {

            if(checkFileIsinTheStagingArea(name))
            removeFromStagingArea(name,hashcode);
            return;
        }


        if(blobs.containsKey(name)){
            if(checkFileIsinTheStagingArea(name)){

                return;
            }else{
                save(false,name);
                add_create(name,hashcode,size_cur,false);
                return;
            }

        }
        else {
            save(false,name);
            add_create(name,hashcode,size_cur,false);
        }


    }

    private static void add_create(String filename,String hashname,int size_cur,boolean hasModify){
        File addfile_new=new File(Repository.CWD,filename);
        File addfilenew =new File(Repository.BLOBS_DIR,hashname);

        link_remove=readObject(REMOVELIST,LinkedList.class);
        link_add=readObject(ADDLIST,LinkedList.class);
        if(!link_add.contains(filename))
            return;


        blobs.put(filename,hashname);
        size_cur++;
        writeObject(BLOBS_MAX,size_cur);
        writeObject(ADDAREA,blobs);
        try{

            addfilenew.createNewFile();
        }catch (IOException o){

        }
        byte[] s=readContents(addfile_new);
        writeContents(addfilenew,s);




    }

    public static void save_remove(String filename,boolean hasCommited){

        if(hasCommited){
            link_remove=readObject(REMOVELIST,LinkedList.class);
            link_remove.addFirst(filename);

            writeContents(REMOVESTATUS,getNameList(link_remove));
            writeObject(REMOVELIST,link_remove);
        }
        else{
            link_remove=readObject(REMOVELIST,LinkedList.class);
            link_remove.addFirst(filename);
            writeContents(REMOVESTATUS,getNameList(link_remove));
            writeObject(REMOVELIST,link_remove);
        }




    }

    public static void save(boolean hasModify,String filename){
        link_add=readObject(ADDLIST,LinkedList.class);
        link_mod=readObject(MODLIST,LinkedList.class);
        if(hasModify)
        {
            link_mod.addFirst(filename);
            if(link_add.contains(filename))
            {
                link_add.remove(filename);
            }
            writeContents(MODSTATUS,getNameList(link_mod));
            writeContents(ADDSTATUS,getNameList(link_add));
            writeObject(MODLIST,link_mod);
            writeObject(ADDLIST,link_add);
        }
        else{

            link_add.addFirst(filename);
            writeContents(ADDSTATUS,getNameList(link_add));
            writeObject(ADDLIST,link_add);

        }

    }


    public static void clearStatus(){
        writeObject(MODLIST,link_mod);
        writeObject(ADDLIST,link_add);
        writeObject(REMOVELIST,link_remove);
        writeContents(ADDSTATUS,"");
        writeContents(MODSTATUS,"");
        writeContents(REMOVESTATUS,"");
    }


    public static HashMap<String,String> getBlobs(){
        HashMap<String,String> b=readObject(ADDAREA,HashMap.class);
        return b;
    }
    public static HashMap<String,String> getRemoval(){
        HashMap<String,String> b=readObject(REMOVEAREA,HashMap.class);
        return b;
    }

    public static boolean hasToCommit(){
        String s=readContentsAsString(ADDSTATUS);
        String s2=readContentsAsString(MODSTATUS);
        String s3=readContentsAsString(REMOVESTATUS);


        return (!s.isEmpty())||(!s2.isEmpty()||(!s3.isEmpty()));
    }
    public static void checkRemove(){
        link_add=readObject(ADDLIST,LinkedList.class);
        link_mod=readObject(MODLIST,LinkedList.class);
        link_remove=readObject(REMOVELIST,LinkedList.class);

        int i=0;
        while(i<link_add.size()){
           
            if(link_remove.contains(link_add.get(i))){
                link_add.remove(i);
            }
            i++;
        }
        writeContents(ADDSTATUS,getNameList(link_add));


        i=0;
        while(i<link_mod.size()){
            if(link_remove.contains(link_mod.get(i))){
                link_mod.remove(i);
            }
            i++;
        }
        writeContents(MODSTATUS,getNameList(link_mod));

    }

    public static void remove(String filename){



        blobs=readObject(ADDAREA,HashMap.class);
        remove_blobs=readObject(REMOVEAREA,HashMap.class);
        link_remove=readObject(REMOVELIST,LinkedList.class);
        link_add=readObject(ADDLIST,LinkedList.class);
        Commit head=ComTreeControler.getHead();


        if(head.getBlobs(filename)==null&&!blobs.containsKey(filename))

            Repository.exit("No reason to remove the file.");

        if(link_remove.contains(filename)){
            System.out.println("this file has already removed and staging into the remove area");
            return ;
        }
        if(link_add.contains(filename)){
            Commit c=ComTreeControler.getHead();
            File f=new File(Repository.CWD,filename);

            blobs.remove(filename);
            link_add.remove(filename);
            writeObject(ADDLIST,link_add);
            writeObject(ADDAREA,blobs);
            writeContents(ADDSTATUS,getNameList(link_add));
            return ;
        }
        Commit c=ComTreeControler.getHead();

        if(c.getBlobs(filename)!=null){

            remove_blobs.put(filename,blobs.get(filename));
            File f=new File(Repository.CWD,filename);
            f.delete();
            writeObject(REMOVEAREA,remove_blobs);


            save_remove(filename,false);

        }
        else {
            if(head.isRemoved(filename)){
                remove_blobs.put(filename,blobs.get(filename));
                File f=new File(Repository.CWD,filename);
                f.delete();
                writeObject(REMOVEAREA,remove_blobs);

                save_remove(filename,true);
                return ;
            }
            System.out.println("this file did not bt added into the staging area or" +
                    "tracked with the head commit");
        }

    }

    public static String getNameList(LinkedList<String> L){
        int i=0;
        String s="";
        while(i<L.size()){
            String a=L.get(i);
            if(a==null){
                s="";
            }else{

                s=s+a+"\n";
            }

            i++;
        }
        return s;
    }


    public static void clearRemoval(){
        link_remove=readObject(REMOVELIST,LinkedList.class);
        HashMap<String,String>  blobs=StagingArea.getBlobs();

        HashMap<String,String> removeblobs=StagingArea.getRemoval();

        if(blobs==null)
            blobs=new HashMap<String,String>();
        if(removeblobs==null)
            removeblobs=new HashMap<String,String>();


        Iterator<String> ite=link_remove.iterator();

        while(ite.hasNext()){
            String name= ite.next();
            String hash_name=removeblobs.get(name);
            if(name==null||hash_name==null)
                continue;


            File f=new File(Repository.CWD,name);
            f.delete();

            blobs.remove(name);
            ite.remove();

        }

        writeObject(ADDAREA,blobs);
        writeObject(REMOVEAREA,new HashMap<String,String>());
        writeObject(REMOVELIST,new LinkedList<String>());

    }


    private static boolean checkFileIsinTheStagingArea(String filename){
        link_add=readObject(ADDLIST,LinkedList.class);
        link_mod=readObject(MODLIST,LinkedList.class);

        if(link_add.contains(filename)||link_mod.contains(filename))
            return true;
        else
            return false;


    }


    private static boolean checkFileWithCWCommit(String filename,String hashcode){
        Commit c=ComTreeControler.getHead();
        return c.isTracked(filename,hashcode);


    }
    private static void removeFromStagingArea(String filename,String new_hash){
        link_add=readObject(ADDLIST,LinkedList.class);

        File f=new File(Repository.BLOBS_DIR,new_hash);
        f.delete();
        if(link_add.contains(filename))
            link_add.remove(filename);
        writeObject(ADDLIST,link_add);
    }


    public static LinkedList<String> getModify(Commit head){
        LinkedList<String> L=new LinkedList<>();
        blobs=readObject(ADDAREA,HashMap.class);
        remove_blobs=readObject(REMOVEAREA,HashMap.class);
        List<String> file_list = plainFilenamesIn(Repository.CWD);
        Set<String> set=head.getblobsSet();
        Set<String> set_stagingarea=blobs.keySet();
        Set<String> set_removearea=remove_blobs.keySet();
        Iterator<String> ite = file_list.iterator();
        while (ite.hasNext()) {
            String s=ite.next();
            File f=new File(Repository.CWD,s);
            String cw_hash=sha1(readContents(f));
            //Tracked in the current commit, changed in the working directory, but not staged;
            if(!checkFileIsinTheStagingArea(s)&&head.isDifferent(s,cw_hash))
            {
                s=s+" (modified)";
                L.addLast(s);
            }

            //Staged for addition, but with different contents than in the working directory
            if(checkFileIsinTheStagingArea(s)&&!getStagingAreahash(s).equals(cw_hash))
            {
                s=s+" (modified)";
                L.addLast(s);
            }


        }


       ite = set_stagingarea.iterator();

        while (ite.hasNext()) {
            String s=ite.next();
            //Staged for addition, but deleted in the working directory
            if(checkFileIsinTheStagingArea(s)&&!file_list.contains(s))
            {
                s=s+" (deleted)";
                L.addLast(s);
            }


        }
        ite = set.iterator();

        while (ite.hasNext()) {
            String s=ite.next();
            //Not staged for removal, but tracked in the current commit and deleted from the working directory.
            if(!set_removearea.contains(s)&&!file_list.contains(s))
            {
                s=s+" (deleted)";
                L.addLast(s);
            }


        }





    return L;




    }
    public static LinkedList<String> getUntrack(Commit head){
        LinkedList<String> L=new LinkedList<>();
        blobs=readObject(ADDAREA,HashMap.class);
        remove_blobs=readObject(REMOVEAREA,HashMap.class);
        List<String> file_list = plainFilenamesIn(Repository.CWD);
        Set<String> set=head.getblobsSet();
        Set<String> set_stagingarea=blobs.keySet();
        Set<String> set_removearea=remove_blobs.keySet();
        Iterator<String> ite = file_list.iterator();

        while (ite.hasNext()) {
            String s=ite.next();
            File f=new File(Repository.CWD,s);
            String cw_hash=sha1(readContents(f));
            //The final category ("Untracked Files") is for files present in the working directory
            // but neither staged for addition nor tracked.
            if(!checkFileIsinTheStagingArea(s)&&!head.istracked(s))
                L.addLast(s);
           //This includes files that have been staged for removal, but then re-created without Gitlet’s knowledge.
            if(set_removearea.contains(s))
                L.addLast(s);

        }
        return L;
    }
    public static String getStagingAreahash(String filename){
        blobs=readObject(ADDAREA,HashMap.class);

        if(blobs==null)
            return "";

        if(blobs.containsKey(filename))
            return blobs.get(filename);

        return "";


    }
}
