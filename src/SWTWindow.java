//Window UI, using JAVA SWT.

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.eclipse.swt.widgets.*; 
import org.eclipse.swt.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;

public class SWTWindow
{
	//Delcaration of all data
	static final String JDBC_DRIVER = "org.postgresql.Driver";
	static UI_Information uiInfo;
//	static MFStructOrig mfStructOrig = new MFStructOrig();
	static InfoSchema infoSchema = new InfoSchema();
	static GenerateMFStructCode genMFStructCode = new GenerateMFStructCode();
	
	static Connection conn;
	
	static Shell shell;
	public static final int SHELL_TRIM = SWT.CLOSE | SWT.TITLE | SWT.MIN | SWT.RESIZE;
	static String openFileDir = null;

	//static text boxes.
	static Text tFileDir;
	static Text tSaveDir;
	static Text tSelectedAttributes;
	static Text tNumofGroupingVar;
	static Text tGroupingAttri;
	static Text tFVect;
	static Text tSelectCondition;
	
	static Text tDBURL;
	static Text tUserName;
	static Text tPsw;
	static Text tTableName;
	
	//main functions
	public static void main(String[] args)
	{
		try
		{
			Class.forName(JDBC_DRIVER);
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		
		//Button Generate code listener.
		class GenerateListener implements SelectionListener
		{
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0)
			{}
			@Override
			public void widgetSelected(SelectionEvent arg0)
			{
				if(uiInfo == null)
				{
					uiInfo = new UI_Information();
				}
				try
				{
					conn = DriverManager.getConnection(uiInfo.strDBURL, uiInfo.strDBUserName, uiInfo.strDBPSW);
					checkInfoSchema();
					infoSchema.setStructTypeJAVA();
//					printInfoSchema(infoSchema);
					//Check output
					MFStructOrig mfStructOrig = new MFStructOrig();
					if(tSelectedAttributes.getText().equals("") == false)
					{
						mfStructOrig.setSelectAttributes(tSelectedAttributes.getText());
					}
					else
					{
						System.out.println("Illegel input!");
						MessageBox mBox = new MessageBox(shell, SWT.ICON_WARNING);
						mBox.setText("Warning!");
						mBox.setMessage("Illegel input!");
						mBox.open();
						return;
					}
					if(tNumofGroupingVar.getText().equals("") == false)
					{
						mfStructOrig.setGroupingAttrNumber(tNumofGroupingVar.getText());
					}
					else
					{
						System.out.println("Illegel input!");
						MessageBox mBox = new MessageBox(shell, SWT.ICON_WARNING);
						mBox.setText("Warning!");
						mBox.setMessage("Illegel input!");
						mBox.open();
						return;
					}
					if(tGroupingAttri.getText().equals("") == false)
					{
						mfStructOrig.setGroupingAttrs(tGroupingAttri.getText());
					}
					else
					{
						System.out.println("Illegel input!");
						MessageBox mBox = new MessageBox(shell, SWT.ICON_WARNING);
						mBox.setText("Warning!");
						mBox.setMessage("Illegel input!");
						mBox.open();
						return;
					}
					if(tFVect.getText().equals("") == false)
					{
						mfStructOrig.setFV(tFVect.getText());
					}
					else
					{
						System.out.println("Illegel input!");
						MessageBox mBox = new MessageBox(shell, SWT.ICON_WARNING);
						mBox.setText("Warning!");
						mBox.setMessage("Illegel input!");
						mBox.open();
						return;
					}
					if(tSelectCondition.getText().equals("") == false)
					{
						mfStructOrig.setConditions(tSelectCondition.getText());
					}
					else
					{
						System.out.println("Illegel input!");
						MessageBox mBox = new MessageBox(shell, SWT.ICON_WARNING);
						mBox.setText("Warning!");
						mBox.setMessage("Illegel input!");
						mBox.open();
						return;
					}
					genMFStructCode = new GenerateMFStructCode();
					genMFStructCode.setClassString(mfStructOrig, infoSchema);
					GenerateMainCode gCode = new GenerateMainCode(tSaveDir.getText(),uiInfo, mfStructOrig, genMFStructCode, infoSchema);
					gCode.printGCode();
					conn.close();
					System.out.println("Codes generated!");
				}
				catch (SQLException e) 
				{
					System.out.println("Cannot connect to Database!");
					MessageBox mBox = new MessageBox(shell, SWT.ICON_WARNING);
					mBox.setText("Warning!");
					mBox.setMessage("Cannot connect to Database!");
					mBox.open();
				}
			}
		}
		
		//check database connection listener.
		class CheckListener implements SelectionListener
		{
			public void widgetDefaultSelected(SelectionEvent arg0)
			{}
			public void widgetSelected(SelectionEvent arg0)
			{
				uiInfo = new UI_Information();
				uiInfo.strDBURL = tDBURL.getText();
				uiInfo.strDBUserName = tUserName.getText();
				uiInfo.strDBPSW = tPsw.getText();
				uiInfo.strTableName = tTableName.getText();
				try
				{
					conn = DriverManager.getConnection(uiInfo.strDBURL, uiInfo.strDBUserName, uiInfo.strDBPSW);
					DatabaseMetaData dbm = conn.getMetaData();
					// check if "employee" table is there
					ResultSet tables = dbm.getTables(null, null, uiInfo.strTableName, null);
					if (tables.next())
					{
						MessageBox mBox = new MessageBox(shell, SWT.ICON_WORKING);
						mBox.setMessage("Successfully connected to Database!\nTable \"" + 
						uiInfo.strTableName + "\"exists!");
						mBox.open();
					}
					else
					{
						MessageBox mBox = new MessageBox(shell, SWT.ICON_WARNING);
						mBox.setText("Warning!");
						mBox.setMessage("Successfully connected to Database!\nTable \"" +
						uiInfo.strTableName + "\" does no exist!");
						mBox.open();
					}
				} 
				catch (SQLException e) 
				{
					System.out.println("Cannot connect to Database!");
					MessageBox mBox = new MessageBox(shell, SWT.ICON_WARNING);
					mBox.setText("Warning!");
					mBox.setMessage("Cannot connect to Database!");
					mBox.open();
				}
			}
		}
		
		//open diretory listener.
		class OpenListener implements SelectionListener
		{
			BufferedReader br = null;
			
			public void widgetSelected(SelectionEvent event) {
				MFStructOrig mfStructTmp = new MFStructOrig();
				FileDialog fd = new FileDialog(shell, SWT.OPEN);
				fd.setText("Open");
				fd.setFilterPath("../DBMS/test_examples");
				String[] filterExt = { "*.txt" };
				fd.setFilterExtensions(filterExt);
				String selected = fd.open();
				if(selected != null)
				{
					System.out.println("Open File Dir:" + selected);
					openFileDir = selected;
					tFileDir.setText(selected);
				}
				try
				{
					br = new BufferedReader(new FileReader(openFileDir));

					String curLine;
					while((curLine = br.readLine()) != null)
					{
						if(curLine.equals("SELECT ATTRIBUTE(S):"))
						{
							mfStructTmp.setSelectAttributes(br, curLine);
							continue;
						}
						else if(curLine.equals("NUMBER OF GROUPING VARIABLES(n):"))
						{
							curLine = br.readLine();
							mfStructTmp.setGroupingAttrNumber(curLine);
							continue;
						}
						else if(curLine.equals("GROUPING ATTRIBUTES(V);"))
						{
							mfStructTmp.setGroupingAttrs(br, curLine);
							continue;
						}
						else if(curLine.equals("F-VECT([F]):"))
						{
							mfStructTmp.setFV(br, curLine);
							continue;
						}
						else if(curLine.equals("SELECT CONDITION-VECT([]):"))
						{
							mfStructTmp.setConditions(br, curLine);
							continue;
						}
					}
				}
				catch (FileNotFoundException e) 
				{
					e.printStackTrace();
				}
				catch (IOException e) 
				{
					e.printStackTrace();
				}
				String strSelAttr = new String();
				for(int i = 0; i != mfStructTmp.lst_Select_Attr.size(); i++)
				{
					strSelAttr += mfStructTmp.lst_Select_Attr.get(i);
					if(i != mfStructTmp.lst_Select_Attr.size() - 1)
					{
						strSelAttr += ", ";
					}
				}
				if(strSelAttr != null)
				{
					tSelectedAttributes.setText(strSelAttr);
				}
				int iNumofGroupVar = 0;
				iNumofGroupVar = mfStructTmp.num_Grouping_Vari;
				if(iNumofGroupVar != 0)
				{
					tNumofGroupingVar.setText(new Integer(iNumofGroupVar).toString());
				}
				String strGroupingAttr = new String();
				for(int i = 0; i != mfStructTmp.lst_Grouping_Attr.size(); i++)
				{
					strGroupingAttr += mfStructTmp.lst_Grouping_Attr.get(i);
					if(i != mfStructTmp.lst_Grouping_Attr.size() - 1)
					{
						strGroupingAttr += ", ";
					}
				}
				if(strGroupingAttr != null)
				{
					tGroupingAttri.setText(strGroupingAttr);
				}
				String strFVect = new String();
				for(int i = 0; i != mfStructTmp.lst_FV.size(); i++)
				{
					strFVect += mfStructTmp.lst_FV.get(i);
					if(i != mfStructTmp.lst_FV.size() - 1)
					{
						strFVect += ", ";
					}
				}
				if(strFVect != null)
				{
					tFVect.setText(strFVect);
				}
				String strSelectionCondition = new String();
				for(int i = 0; i != mfStructTmp.lst_Conditions.size(); i++)
				{
					strSelectionCondition += mfStructTmp.lst_Conditions.get(i);
					if(i != mfStructTmp.lst_Conditions.size() - 1)
					{
						strSelectionCondition += ", ";
					}
				}
				if(strSelectionCondition != null)
				{
					tSelectCondition.setText(strSelectionCondition);
				}
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		}
		
		//save diretory button listener.
		class SaveListener implements SelectionListener
		{
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				DirectoryDialog dd = new DirectoryDialog(shell);
				dd.setText("Save");
				dd.setMessage("Select a directory");
				dd.setFilterPath("./");
				String selected = dd.open();
				if(selected != null)
				{
					System.out.println("Save Files Dir:" + selected);
					tSaveDir.setText(selected);
				}
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0)
			{}
		}

		Display display = new Display(); 
		
		shell = new Shell(display, SHELL_TRIM & (~SWT.RESIZE));
		
		//2 Tabs
		TabFolder folder = new TabFolder(shell, SWT.NULL);
		Composite compAll0 = new Composite(folder, SWT.NULL);
		compAll0.setLayout(new GridLayout(1, false));
		compAll0.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		shell.setLayout(new GridLayout(1, false));

		//tab #1
		TabItem tab0 = new TabItem(folder, SWT.NULL);
		tab0.setText("Inputs");
		
		//file input
		Group group0 = new Group(compAll0, SWT.SHADOW_IN);
		group0.setText("Please choose a file");

		group0.setLayout(new GridLayout(2, false));
		group0.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false));
		tFileDir = new Text(group0, SWT.BORDER);
		GridData gridData = new GridData();
		gridData.widthHint = 270;
		tFileDir.setLayoutData(gridData);
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		Button btLoadFile = new Button(group0, SWT.PUSH);
		btLoadFile.setText("browse");
		btLoadFile.setLayoutData(gridData);
		btLoadFile.setSize(btLoadFile.computeSize(SWT.NONE, SWT.DEFAULT));
		btLoadFile.addSelectionListener(new OpenListener());

		//textboxes for user to input
		Group group1 = new Group(compAll0, SWT.SHADOW_IN);
		group1.setText("input variables:");
		group1.setLayout(new GridLayout(1, false));
		group1.setLayoutData(new GridData(SWT.FILL, SWT.UP, true, false));
		Label lSelectedAttributes = new Label(group1, SWT.RIGHT);
		lSelectedAttributes.setText("selected attributes:");
		tSelectedAttributes = new Text(group1, SWT.BORDER);
		GridData gd1 = new GridData();
		gd1.widthHint = 360;
		tSelectedAttributes.setLayoutData(gd1);
		Label lNumofGroupingVar = new Label(group1, SWT.RIGHT);
		lNumofGroupingVar.setText("Number of Gropuing Variables:");
		tNumofGroupingVar = new Text(group1, SWT.BORDER);
		tNumofGroupingVar.setLayoutData(gd1);
		Label lGroupingAttri = new Label(group1, SWT.RIGHT);
		lGroupingAttri.setText("Grouping Attributes:");
		tGroupingAttri = new Text(group1, SWT.BORDER);
		tGroupingAttri.setLayoutData(gd1);
		Label lFVect = new Label(group1, SWT.RIGHT);
		lFVect.setText("F-Vect:");
		tFVect = new Text(group1, SWT.BORDER);
		tFVect.setLayoutData(gd1);
		Label lSelectCondition = new Label(group1, SWT.RIGHT);
		lSelectCondition.setText("Select Condition-Vect:");
		tSelectCondition = new Text(group1, SWT.BORDER);
		tSelectCondition.setLayoutData(gd1);
		
		//generate code dirtory.
		Group group2 = new Group(compAll0, SWT.SHADOW_IN);
		group2.setText("Generated codes directory:");
		group2.setLayout(new GridLayout(2, false));
		group2.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false));
		tSaveDir = new Text(group2, SWT.BORDER);
		tSaveDir.setText("./");
		GridData gd2 = new GridData();
		gd2.widthHint = 270;
		tSaveDir.setLayoutData(gd2);
		Button btSaveDir = new Button(group2, SWT.PUSH);
		btSaveDir.setLayoutData(gridData);
		btSaveDir.setText("browse");
		btSaveDir.setSize(btSaveDir.computeSize(SWT.NONE, SWT.DEFAULT));
		btSaveDir.addSelectionListener(new SaveListener());
		
		Composite comp0 = new Composite(compAll0, SWT.NONE);
		comp0.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, true, false));
		
		FormLayout fl = new FormLayout();
		fl.marginTop = 5;
		fl.marginRight = 30;
		comp0.setLayout(fl);
		Button btGenerateCode = new Button(comp0,SWT.CENTER);
		btGenerateCode.setText("generate!");
		
		btGenerateCode.addSelectionListener(new GenerateListener());
		
		tab0.setControl(compAll0);
		
		//Tab #2
		TabItem tab1 = new TabItem(folder, SWT.NULL);
		tab1.setText("DB Preferences");
		Composite compAll1 = new Composite(folder, SWT.NULL);
		tab1.setControl(compAll1);
		compAll1.setLayout(new GridLayout(1, false));
		compAll1.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		//DB URL, user, psw, table...
		Composite comp10 = new Composite(compAll1, SWT.NULL);
		comp10.setLayout(new GridLayout(2, false));
		comp10.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		
		Label lDBURL = new Label(comp10, SWT.NULL);
		lDBURL.setText("DB URL:");
		lDBURL.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, true, false));
		tDBURL = new Text(comp10, SWT.BORDER);
		GridData gd3 = new GridData();
		gd3.horizontalAlignment = SWT.LEFT;
        gd3.verticalAlignment = SWT.TOP;
        gd3.grabExcessHorizontalSpace = false;
        gd3.grabExcessVerticalSpace = false;
		gd3.widthHint = 270;
		tDBURL.setLayoutData(gd3);
		tDBURL.setText("jdbc:postgresql://localhost:5432/DBMS");
		
		Composite comp11 = new Composite(compAll1, SWT.NULL);
		comp11.setLayout(new GridLayout(2, false));
		comp11.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		
		Label lUserName = new Label(comp11, SWT.NULL);
		lUserName.setText("DB username:");
		lUserName.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, true, false));
		tUserName = new Text(comp11, SWT.BORDER);
		tUserName.setText("postgres");
		tUserName.setLayoutData(gd3);
		
		Composite comp12 = new Composite(compAll1, SWT.NULL);
		comp12.setLayout(new GridLayout(2, false));
		comp12.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		
		Label lPassword = new Label(comp12, SWT.NULL);
		lPassword.setText("Password:");
		lPassword.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, true, false));
		tPsw = new Text(comp12, SWT.PASSWORD | SWT.BORDER);
		tPsw.setLayoutData(gd3);
		
		Composite comp13 = new Composite(compAll1, SWT.NULL);
		comp13.setLayout(new GridLayout(2, false));
		comp13.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		
		Label lTableName = new Label(comp13, SWT.NULL);
		lTableName.setText("Table name:");
		lTableName.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, true, false));
		tTableName = new Text(comp13, SWT.BORDER);
		tTableName.setText("sales");
		tTableName.setLayoutData(gd3);
		
		Composite comp14 = new Composite(compAll1, SWT.NULL);
		comp14.setLayoutData(new GridData(SWT.RIGHT, SWT.TOP, false, true));
		comp14.setLayout(fl);
		
		Button btCheckDB = new Button(comp14, SWT.CENTER);
		btCheckDB.setText("Check");
		btCheckDB.addSelectionListener(new CheckListener());
		
		
		shell.pack(); 
		btLoadFile.pack();

		shell.setSize(435, 550);
		shell.setLocation(250, 100);

		shell.open(); 
		while(!shell.isDisposed()) 
			if(!display.readAndDispatch()) 
				display.sleep(); 
		display.dispose(); 
		btLoadFile.dispose();
	}
	
	//check information_schema from database
	private static void checkInfoSchema()
	{
		if(uiInfo == null)
		{
			uiInfo = new UI_Information();
		}
		String SQLQuery = "select column_name, data_type from information_schema.columns\n"
				+ "where table_name = '" + uiInfo.strTableName + "'";
		infoSchema = new InfoSchema();
		try
		{
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(SQLQuery);
			while(rs.next())
			{
				String col = rs.getString("column_name");
				String type = rs.getString("data_type");
				infoSchema.addValue(col, type);
			}
//			printInfoSchema(infoSchema);
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
		}
	}
	
	//print informations schemas, check if they are right.
	@SuppressWarnings("unused")
	private static void printInfoSchema(InfoSchema infoSchema)
	{
		for(int i = 0; i != infoSchema.getList().size(); i++)
		{
			System.out.println( infoSchema.getList().get(i).getFirst() + "\t"
					+ infoSchema.getList().get(i).getSecond());
		}
	}
}