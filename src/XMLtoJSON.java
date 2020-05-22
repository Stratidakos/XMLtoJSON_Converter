import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;
import org.json.XML;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Ph1 {
    public static void main(String[] args) throws IOException, NullPointerException{


        int rcn;
        Object acronym;
        String title;
        String objective;
        String identifier;
        String text;
        String line,str,link;
        String directory= "Parsed files";
        File dir = new File(directory);
        File[] files = dir.listFiles();
        JSONObject[] obj = new JSONObject[files.length];
        int i=0;
        Project project=new Project();
        ObjectMapper mapper = new ObjectMapper();
        File currentDirectory = new File(new File(".").getAbsolutePath());
        new File(currentDirectory.getCanonicalPath()+"/projects").mkdirs();

        for(File f : files) {

            str = "";
            link = f.getAbsolutePath();
            BufferedReader br = new BufferedReader(new FileReader(link));
            while ((line = br.readLine()) != null) {
                str += line;
            }
            obj[i] = XML.toJSONObject(str);
            obj[i].getJSONObject("project").remove("xmlns");
            rcn = obj[i].getJSONObject("project").getInt("rcn");
            acronym = obj[i].getJSONObject("project").get("acronym");
            title = obj[i].getJSONObject("project").getString("title");
            objective = obj[i].getJSONObject("project").getString("objective");
            identifier = obj[i].getJSONObject("project").getString("identifier");

            text = title + objective;
            obj[i].getJSONObject("project").remove("title");
            obj[i].getJSONObject("project").remove("objective");
            obj[i].getJSONObject("project").put("text",text);

            project.rcn= rcn;
            project.acronym= acronym;
            project.text= text;
            project.identifier= identifier;
            i++;

            try {

                // Writing to a file
                mapper.writeValue(new File(currentDirectory.getCanonicalPath()+"/projects/project"+ String.valueOf(i) +".json"), project );

            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("{\"index\":{\"_id\":\"" + i +"\"}}");
            System.out.println(obj[i-1].toString());
        }
    }
}
