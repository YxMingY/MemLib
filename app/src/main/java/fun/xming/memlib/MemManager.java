package fun.xming.memlib;
import android.os.Environment;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.StringWriter;
import java.util.Map;
import java.util.LinkedHashMap;
import org.yaml.snakeyaml.Yaml;
import java.io.File;

public class MemManager {
    
    // 真实位置【/mnt/sdcard/Android/data/<package_name>/files/Movies】
// 模拟位置【/storage/emulated/0/Android/data/<package_name>/files/Movies】
	private String pathExternalFiles = "";
	private LinkedHashMap<String,Map> contents;
    
	public MemManager(MainActivity act) {
		
		pathExternalFiles = act.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath();
		try{
			File file = new File(pathExternalFiles+"/contents.yml");
			if (!file.exists()) {
				try {
					file.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
				act.alert("init");
			} else {
				act.alert("welcome");
			}
			String str = readExternal(pathExternalFiles+"/contents.yml");
			Yaml yaml = new Yaml();
			if(!"".equals(str)) {
        		Object ret = yaml.load(str);
				contents = (LinkedHashMap<String,Map>)ret;
			}else{
				contents = new LinkedHashMap<String,Map>();
                //writeExternal(pathExternalFiles+"/contents.yml","");
            }
		}catch(IOException e){
			MainActivity.alert(e.toString());
		}
	}

    public boolean exists(String name) {
        return getMemMap().containsKey(name);
    }

    public LinkedHashMap getMemMap() {
        return contents;
    }

    public void addMem(String name,LinkedHashMap map) {
        contents.put(name,map);
        mapToFile();
    }
    public void dropMem(String name) {
        contents.remove(name);
        mapToFile();
    }

    private void mapToFile() {
        try{
            Yaml yaml = new Yaml();
            StringWriter sw = new StringWriter();
            yaml.dump(contents,sw);
            writeExternal(pathExternalFiles+"contents.yml",sw.toString());
        }catch(IOException e){}
    }
	
	/**
     * 从External文件目录下读取文件
     *
     * @param filePathName 要读取的文件的路径+文件名
     * @return
     * @throws IOException
     */
    public static String readExternal(String filePathName) throws IOException {
        StringBuffer stringBuffer = new StringBuffer();

        // 获取External的可用状态
        String storageState = Environment.getExternalStorageState();

        if (storageState.equals(Environment.MEDIA_MOUNTED)) {
            // 当External的可用状态为可用时

            // 打开文件输入流
            FileInputStream fileInputStream = new FileInputStream(filePathName);

            byte[] buffer = new byte[1024];
            int len = fileInputStream.read(buffer);
            // 读取文件内容
            while (len > 0) {
                stringBuffer.append(new String(buffer, 0, len));

                // 继续把数据存放在buffer中
                len = fileInputStream.read(buffer);
            }

            // 关闭输入流
            fileInputStream.close();
        }
        return stringBuffer.toString();
    }
	/**
     * 向External文件目录下写入文件
     *
     * @param filePathName 要写入的的文件的路径+文件名
     * @param content      要写入的内容
     * @throws IOException
     */
    public static void writeExternal(String filePathName, String content) throws IOException {
        // 获取External的可用状态
        String storageState = Environment.getExternalStorageState();

        if (storageState.equals(Environment.MEDIA_MOUNTED)) {
            // 当External的可用状态为可用时

            // 打开输出流
            FileOutputStream fileOutputStream = new FileOutputStream(filePathName);

            // 写入文件内容
            fileOutputStream.write(content.getBytes());

            // 关闭输出流
            fileOutputStream.close();

        }
    }
}
