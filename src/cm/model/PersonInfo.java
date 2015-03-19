package cm.model;
import java.util.Arrays;


public class PersonInfo 
{
	private String usercount;//账号
	private String username;//昵称
	public int num;//题目总数
	private String registertime;//注册时间
	private String rank;//杭电排名
	private String totalprosubmitted;//提交过的题数
	private String totalprosolved;//解决过的题数
	private String totalsubmissions;//提交过的次数
	private String totalaccepted;//解决过的次数
	public ProStruct arrayprostruct[]=new ProStruct[5000];//解决过的题目信息(包括题号pronum,时间protime,提交次数prosub,AC的次数proac)
	
	//PersinInfo的构造函数，没参数
	public PersonInfo()
	{
		for(int i=0;i<5000;i++)
		{
			arrayprostruct[i]=new ProStruct();
		}
	}
	
	
	
	//Setter方法与Getter方法
	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRegistertime() {
		return registertime;
	}

	public void setRegistertime(String registertime) {
		this.registertime = registertime;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	public String getTotalprosubmitted() {
		return totalprosubmitted;
	}

	public void setTotalprosubmitted(String totalprosubmitted) {
		this.totalprosubmitted = totalprosubmitted;
	}

	public String getTotalprosolved() {
		return totalprosolved;
	}

	public void setTotalprosolved(String totalprosolved) {
		this.totalprosolved = totalprosolved;
	}

	public String getTotalsubmissions() {
		return totalsubmissions;
	}

	public void setTotalsubmissions(String totalsubmissions) {
		this.totalsubmissions = totalsubmissions;
	}

	public String getTotalaccepted() {
		return totalaccepted;
	}

	public void setTotalaccepted(String totalaccepted) {
		this.totalaccepted = totalaccepted;
	}
	
	
	
	//ProStruct的set方法与get方法，参数是第index个Problem
	public ProStruct getProStruct(int index)
	{
		return arrayprostruct[index];
	}
	public void setProStruct(ProStruct p,int i)
	{
		arrayprostruct[i]=p;
	}
	
	
	//用于按时间排序
	public void sortTime()
	{
		ProStructComparator cmp=new ProStructComparator();
		Arrays.sort(arrayprostruct,0,num, cmp);
	}



	public String getUsercount() {
		return usercount;
	}



	public void setUsercount(String usercount) {
		this.usercount = usercount;
	}
}
