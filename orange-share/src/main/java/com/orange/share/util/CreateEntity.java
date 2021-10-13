package com.orange.share.util;

import java.io.*;
import java.util.Date;
import java.util.Map;


/**
 * Created by Ji ZiYang Damien on 9/25/2017
 */
public class CreateEntity {

    /**
     * 指定实体生成所在包的路径
     */
    private String packageOutPath = "com.pm.model.dynamic";

    /**
     * 作者名字
     */
    private String authorName = "Damien Yu";

    private String tablename;//表名

    private String[] colnames; // 列名数组

    private String[] colTypes; //列名类型数组

    private int[] colSizes; //列名大小数组

    private boolean f_util = false; // 是否需要导入包java.util.*

    private boolean f_sql = false; // 是否需要导入包java.sql.*
    //数据库连接



    /**
     * 构造函数
     */
    public void createEntiy(String tableName, Map<String,String> map) throws Exception {
        this.tablename=tableName;
        //System.getProperty("com.orange.user.dir")
        String xml_Path="E:\\IDEPorject\\pm2"+File.separator+"src"+File.separator+"main"+File.separator+"resources"+File.separator+"dynamic.hbm.xml";
        String outputPath ="E:\\IDEPorject\\pm2"+File.separator+"src"+File.separator+"main"+File.separator+"java"+File.separator + this.packageOutPath.replace(".", File.separator) + File.separator + initcap(tablename) + ".java";
        colnames = new String[map.size()+1];
        colTypes = new String[map.size()+1];
        colSizes = new int[map.size()+1];

        int i=0;
        for(String s:map.keySet()){
            colnames[i+1]=s;
            colTypes[i+1]=map.get(s);
            i++;
        }
        colnames[0]="id";
        colTypes[0]="varchar(32)";
        String content = parse_Entity(colnames, colTypes, colSizes);
        OutputStreamWriter out2 = new OutputStreamWriter(new FileOutputStream(outputPath),"UTF-8");
        //FileWriter fw = new FileWriter(outputPath);
        out2.write(content);
        out2.flush();
        out2.close();
        FileReader fr=new FileReader(xml_Path);
        BufferedReader br=new BufferedReader(fr);
        String str=null,xml_content="";
        while((str=br.readLine())!=null){
            xml_content+=str+"\n";
        }
        fr.close();

        String[] XCN=xml_content.split("</hibernate-mapping>");
        xml_content=parse_Xml(colnames);

        OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(xml_Path),"UTF-8");
        out.write(XCN[0]+xml_content);
        out.flush();
        out.close();

    }
    /**
     * 功能：生成实体类主体代码
     *
     * @param colnames
     * @param colTypes
     * @param colSizes
     * @return
     */
    private String parse_Entity(String[] colnames, String[] colTypes, int[] colSizes) {
        StringBuffer sb = new StringBuffer();
        sb.append("package " + this.packageOutPath + ";\r\n");
        //判断是否导入工具包
        if (f_util) {
            sb.append("import java.util.Date;\r\n");
        }
        if (f_sql) {
            sb.append("import java.sql.*;\r\n");
        }
        sb.append("\r\n");
        //注释部分
        sb.append("   /**\r\n");
        sb.append("    * " + tablename + " 实体类\r\n");
        sb.append("    * " + new Date()+ " " + this.authorName + "\r\n");
        sb.append("    */ \r\n");
        //实体部分
        sb.append("\r\n\r\npublic class " + initcap(tablename) + "{\r\n");
        processAllAttrs(sb);//属性
        processAllMethod(sb);//get set方法
        sb.append("}\r\n");
        //System.out.println(sb.toString());
        return sb.toString();
    }


    /**
     * 生成映射文件
     */

    private String parse_Xml(String[] colnames) {
        StringBuilder sb=new StringBuilder();
        sb.append("<class name=\""+packageOutPath+"."+initcap(tablename)+"\" table=\""+this.tablename+"\">\r\n");
        sb.append("<id name=\"id\">\n" +
                "            <generator class=\"uuid\" ></generator>\n" +
                "        </id>\n");
        for (int i=1;i<colnames.length; i++){
            sb.append("<constant name=\""+colnames[i]+"\"></constant>\n");
        }
        sb.append("</class>\n");
        sb.append("</hibernate-mapping>\n");
        return sb.toString();
    }

    /**
     * 功能：生成所有属性
     *
     * @param sb
     */

    private void processAllAttrs(StringBuffer sb) {
        for (int i = 0; i < colnames.length; i++) {
            sb.append("\tprivate " + sqlType2JavaType(colTypes[i]) + " " + colnames[i] + ";\r\n");
        }
    }

    /**
     * 功能：生成所有方法
     *
     * @param sb
     */
    private void processAllMethod(StringBuffer sb) {
        for (int i = 0; i < colnames.length; i++) {
            sb.append("\tpublic void set" + initcap(colnames[i]) + "(" + sqlType2JavaType(colTypes[i]) + " " + colnames[i] + "){\r\n");
            sb.append("\tthis." + colnames[i] + "=" + colnames[i] + ";\r\n");
            sb.append("\t}\r\n");
            sb.append("\tpublic " + sqlType2JavaType(colTypes[i]) + " get" + initcap(colnames[i]) + "(){\r\n");
            sb.append("\t\treturn " + colnames[i] + ";\r\n");
            sb.append("\t}\r\n");
        }
    }

    /**
     * 功能：将输入字符串的首字母改成大写
     *
     * @param str
     * @return
     */
    private String initcap(String str) {
        char[] ch = str.toCharArray();
        if (ch[0] >= 'a' && ch[0] <= 'z') {
            ch[0] = (char) (ch[0] - 32);

        }
        return new String(ch);
    }


    /**
     * 功能：获得列的数据类型
     *
     * @param sqlType
     * @return
     */
    private String sqlType2JavaType(String sqlType) {
        String s= new String();
         String[] ss=  sqlType.split("\\(");
        sqlType=ss[0];

        if (sqlType.equalsIgnoreCase("bit")) {
            return "boolean";

        } else if (sqlType.equalsIgnoreCase("tinyint")) {
            return "byte";
        } else if (sqlType.equalsIgnoreCase("smallint")) {
            return "short";
        } else if (sqlType.equalsIgnoreCase("int")) {
            return "int";
        } else if (sqlType.equalsIgnoreCase("bigint")) {
            return "long";
        } else if (sqlType.equalsIgnoreCase("float")) {
            return "float";
        } else if (sqlType.equalsIgnoreCase("decimal") || sqlType.equalsIgnoreCase("numeric")
                || sqlType.equalsIgnoreCase("real") || sqlType.equalsIgnoreCase("money")
                || sqlType.equalsIgnoreCase("smallmoney")) {
            return "double";
        } else if (sqlType.equalsIgnoreCase("varchar") || sqlType.equalsIgnoreCase("char")
                || sqlType.equalsIgnoreCase("nvarchar") || sqlType.equalsIgnoreCase("nchar")
                || sqlType.equalsIgnoreCase("text")) {
            return "String";
        } else if (sqlType.equalsIgnoreCase("datetime")) {
            return "Date";
        } else if (sqlType.equalsIgnoreCase("image")) {
            return "Blod";
        }else if(sqlType.equalsIgnoreCase("date")){
            return "Date";
        }

        return null;
    }

}
