package fun.xming.memlib;
 
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ActionBar;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.app.ActivityManager;

public class MainActivity extends Activity { 

	MemManager memManager;
	private static MainActivity act;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		act = this;
		memManager = new MemManager(this);
		ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        setContentView(R.layout.activity_main);
        
    }
	public static void alert(String str) {
		Toast.makeText(act,str,Toast.LENGTH_SHORT).show();
	}
	public void addMem(View view) {
		//final String[] items3 = new String[]{"条目1", "条目2", "条目3", "条目4","条目5", "条目6", "条目7", "条目8"};//创建item
        final EditText et = new EditText(this);
		AlertDialog alertDialog3 = new AlertDialog.Builder(this)
			.setTitle("")
		.setPositiveButton("Submit", new DialogInterface.OnClickListener() { 
				@Override 
				public void onClick(DialogInterface dialog, int which) { 
					//View v = //findViewById(R.id.addmemdlg);
					if(et != null){
						String t = et.getText().toString();
						//alert(t);
						if (t.isEmpty() || memManager.exists(t)) {
							alert("输入为空或该项目已存在");
						} else {
							alert(t);
							Intent intent = new Intent(MainActivity.this,addMem.class);
							startActivity(intent);
						}
				    }else{
						alert("null");
					}
				} 
			}). 
			setNegativeButton("Cancel", new DialogInterface.OnClickListener() { 

				@Override 
				public void onClick(DialogInterface dialog, int which) { 
					// TODO Auto-generated method stub  
				} 
			}). 
			create();
		//View dialogView = View.inflate(MainActivity.this, R.layout.addmemdlg, null);
		alertDialog3.setView(et);
		
        alertDialog3.show();
	}
	public void addMem2(View view) {
		EditText v = (EditText) findViewById(R.id.addmemtxt);
		
	}
	public void revMem(View view) {
		
	}

	public void drpMem(View view) {

	}
	
	private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE" };
	
} 
