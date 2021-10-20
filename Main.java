import java.math.*;
import java.util.*;
class Account implements Comparable<Account>{
  UUID uid;
  String id,accountName;
  Account parentAccount;
  BigDecimal revenue;
  List<Contact> contactList = new ArrayList();
  List<Oppurtunity> oppurtunities= new ArrayList();
  HashMap<Contact,Contact> ReportingMap;
  public Account(String name)
  {
    this.uid=UUID.randomUUID();
    this.id=uid.toString();
    this.accountName=name;
    this.revenue=new BigDecimal(0);
  }
  public Account(String name,Account parent)
  {
    UUID uuid=UUID.randomUUID();
    id=uuid.toString();
    this.accountName=name;
    this.parentAccount=parent;
  }
  public void addContact(Contact con)
  {
    contactList.add(con);
  }
  public void addContacts(List<Contact> con)
  {
    this.contactList.addAll(con);
    if(this.parentAccount!=null)
    this.parentAccount.addContacts(con);
    System.out.println("ContactList updated for the account "+accountName);
  }
  public void addOpputunity(Oppurtunity opp)
  {
    oppurtunities.add(opp);
    System.out.println("Oppurtunites updated for the account "+this.accountName);
  }
  public void addOppurtunities(List<Oppurtunity> con)
  {
    oppurtunities.addAll(con);
    System.out.println("Oppurtunites updated for the account "+this.accountName);
  }
  public void addRevenue()
  {
    for(Oppurtunity opp:oppurtunities)
    {
      revenue=revenue.add(opp.getAmount());
    }
  }
  public BigDecimal getRevenue()
    {
      return revenue;
      }
  public UUID getID()
    {
      return uid;
      }
  public List<Contact> getContacts()
      {
        return this.contactList;
      }
  public List<Oppurtunity> getOppurtunityList()
      {
        return this.oppurtunities;
      }
  public String toString()
    {
      return this.accountName;

    }
  public int compareTo(Account o) {
        return this.getRevenue().compareTo(o.getRevenue());
    }
  public void generateReportingMap()
  {
    ReportingMap=new HashMap<Contact,Contact>();
    for(Contact con: contactList)
    {
      if(con.getReportsTo()==null)
      ReportingMap.put(con,null);
      else
      ReportingMap.put(con,con.getReportsTo());

    }
     for(Map.Entry<Contact,Contact> pair: ReportingMap.entrySet())
    {
      System.out.println("Contact : "+(pair.getKey().getName())+" \n Reprots to  :"+pair.getValue());
      System.out.println();
    }
  }
  public String getPath(Contact src,Contact des)
  {
    String path=src.getName()+" ->";
    Contact current=src;
    while(current!=des&&current!=null)
    {
      current=ReportingMap.get(current);
      path+=current.getName();
    
    }
    return path;
  }

  public String getName()
  {
    return this.accountName;
  }
    
}
//Account class ends here
class Contact{
  String id,f_name,l_name,email,phone;
  Account belongs_account;
  Contact reportsTo;
   public Contact(String F_name,String L_name,String Email,String Phone,Account acc)
  {
    UUID uuid=UUID.randomUUID();
    id=uuid.toString();
    this.f_name=F_name;
    this.l_name=L_name;
    this.email=Email;
    this.phone=Phone;
    this.belongs_account=acc;
    }
  public Contact(String F_name,String L_name,String Email,String Phone,Account acc,Contact reportTo)
  {
    UUID uuid=UUID.randomUUID();
    id=uuid.toString();
    this.f_name=F_name;
    this.l_name=L_name;
    this.email=Email;
    this.phone=Phone;
    this.belongs_account=acc;
    this.reportsTo=reportTo;
  }
  public void changeReportsTo(Contact reportTo)
  {
    this.reportsTo=reportTo;
  }
   public Contact getReportsTo()
  {
    return this.reportsTo;
  }
  public String toString()
  {
    String output="\n Name: "+f_name+" "+l_name+"\n email: "+email+" \n Phone: "+phone+" \n Account "+belongs_account+" \n Reports to:"+ reportsTo;
    return output;
  }
  public String getName()
  {
    return this.f_name;
  }
}
//Contact class ends here
class Oppurtunity{
  String id,name,stage;
  Account account;
  Date closeDate;
  BigDecimal amount;
  public Oppurtunity(String name,Account acc,Date clsDate,BigDecimal amnt,String stage)
  {
    UUID uuid=UUID.randomUUID();
    id=uuid.toString();
    this.name=name;
    this.account=acc;
    this.closeDate=clsDate;
    this.amount=amnt;
    this.stage=stage;
  }
   public BigDecimal getAmount()
  {
    return this.amount;
  }
  public String getStage()
  {
    return this.stage;
  }
  public String toString()
  {
    return this.name;
  }
}
//Oppurtunity class ends here

//
class Main {
  static void listAccounts(List<Account> accounts)
  {
    BigDecimal  targetRevenue=new BigDecimal(100000);
    System.out.println("Accounts with revenue greater than "+targetRevenue);
        for (Account acc : accounts) {
          if(targetRevenue.compareTo(acc.getRevenue())==-1)
            System.out.println(acc);
        }
  }
  static void sortAccounts_stage(List<Account> accounts,String stage)
  { 
    System.out.println("Sorted list of Accounts(based on revnue) whose Oppurtunity stage is " + stage);
    List<Account> result=new LinkedList<Account>();
    for(Account acc : accounts)
    {
      for (Oppurtunity opp: acc.oppurtunities)
      {
        if (opp.getStage()==stage)
        result.add(acc);
      }
    }
 Collections.sort(result);
 System.out.println(result);
  } 
  static void contactReach(HashMap<Contact,Contact> ReportingMap)
  {

  }
  public static void main(String[] args) {
    Account Google=new Account("Google");
    Account Microsoft=new Account("Microsoft");
    Account Amazon=new Account("Amazon");
    List<Account> accountList=new ArrayList<Account>();
    accountList.add(Google);
    accountList.add(Microsoft);
    accountList.add(Amazon);
    Contact c1=new Contact("Dinesh","Koppula","dinesh.koppula@google.com","9573926340",Google);
    Contact c2=new Contact("Surabhi","T.S","Surabhi.ts@microsoft.com","9573926341",Microsoft);
    Contact c3=new Contact("Karan","Sangatani","karan.Sangatania@amazon.com","9573926342",Amazon);
    Contact c4=new Contact("Surabhi","T.S","Surabhi.Ts@google.com","9573926343",Google,c1);
    Contact c5=new Contact("Malek","T","malek@google.com","9573926343",Google,c1); 
    Contact c6=new Contact("Kamal","s","kamal.s@google.com","9573926344",Google,c4); 
    Contact c7=new Contact("Rajesh","Enumula","Rajesh.Enumula@microsoft.com","9573926345",Microsoft,c2);
    Contact c8=new Contact("Praveen","Koppula","Praveen.Koppula@microsoft.com","9573926346",Microsoft,c2);
    Contact c9=new Contact("Ramesh","Enumula","Ramesh.Enumula@microsoft.com","9573926347",Microsoft,c8);
    Contact c10=new Contact("Rajeev","Baira","Rajeev.Baira@amazon.com","9573926348",Amazon,c3);
    Contact c11=new Contact("Arun","Ravula","arun.Ravula@amazon.com","9573926349",Amazon,c3);
    Contact c12=new Contact("Sandeep","Gajjela","Sandeep.Gajjela@microsoft.com","9573926350",Amazon,c3);
    List<Contact> googleContact =new ArrayList<>();
    List<Contact> microsoftContact =new ArrayList<>();
    List<Contact> amazonContact =new ArrayList<>();
    List<Oppurtunity> googleOppurtunity =new ArrayList<>();
    List<Oppurtunity> microsoftOppurtunity =new ArrayList<>();
    List<Oppurtunity> amazonOppurtunity =new ArrayList<>();

    //adding contacts to googleContact list
    googleContact.add(c1);
    googleContact.add(c4);
    googleContact.add(c5);
    googleContact.add(c6);
    //adding contacts to microsoftContact list
    microsoftContact.add(c2);
    microsoftContact.add(c7);
    microsoftContact.add(c8);
    microsoftContact.add(c9);
    //adding contacts to amazonContact list
    amazonContact.add(c3);
    amazonContact.add(c10);
    amazonContact.add(c11);
    amazonContact.add(c12);
   //updating contact list in accounts;
    Google.addContacts(googleContact);
    Microsoft.addContacts(microsoftContact);
    Amazon.addContacts(amazonContact);
    //creating and updating opputinuties for google
    googleOppurtunity.add(new Oppurtunity("a",Google,new Date(9573926340L),new BigDecimal(945689),"prospecting"));
    googleOppurtunity.add(new Oppurtunity("b",Google,new Date(9573726340L),new BigDecimal(987654),"closed"));
    googleOppurtunity.add(new Oppurtunity("c",Google,new Date(9573866340L),new BigDecimal(945621),"active"));
    //creating and updating opputinuties for Microsoft
    microsoftOppurtunity.add(new Oppurtunity("d",Microsoft,new Date(9573563340L),new BigDecimal(456321),"active"));
    microsoftOppurtunity.add(new Oppurtunity("e",Microsoft,new Date(9573840340L),new BigDecimal(893963),"prospecting"));
    microsoftOppurtunity.add(new Oppurtunity("f",Microsoft,new Date(9573456340L),new BigDecimal(748856),"closed"));
    //creating and updating opputinuties for Amazon
    amazonOppurtunity.add(new Oppurtunity("g",Amazon,new Date(9573392340L),new BigDecimal(912125),"closed"));
    amazonOppurtunity.add(new Oppurtunity("h",Amazon,new Date(9573854340L),new BigDecimal(678956),"active"));
    amazonOppurtunity.add(new Oppurtunity("i",Amazon,new Date(9573786340L),new BigDecimal(888986),"prospecting"));

    Google.addOppurtunities(googleOppurtunity);
    Microsoft.addOppurtunities(microsoftOppurtunity);
    Amazon.addOppurtunities(amazonOppurtunity);
    Google.addRevenue();
    Microsoft.addRevenue();
    Amazon.addRevenue();
    listAccounts(accountList);
    sortAccounts_stage(accountList,"prospecting");
    HashMap<UUID,List<Contact>> accContact=new HashMap<UUID,List<Contact>>();
    HashMap<UUID,List<Oppurtunity>> accOppurtunity=new HashMap<UUID,List<Oppurtunity>>();
    for(Account acc : accountList)
    {
      accContact.put(acc.getID(),acc.getContacts());
      accOppurtunity.put(acc.getID(),acc.getOppurtunityList());
    }
    for(Map.Entry<UUID,List<Contact>> pair: accContact.entrySet())
    {
      System.out.println("key : "+pair.getKey()+" \nValue :"+pair.getValue());
    }
      for(Map.Entry<UUID,List<Oppurtunity>> pair: accOppurtunity.entrySet())
    {
      System.out.println("key : "+pair.getKey()+"\n Value :"+pair.getValue());
    }
    for(Account acc : accountList)
    {
      System.out.println("Reporting List for Accoutn "+acc.getName());
      acc.generateReportingMap();
      System.out.println("\n");
    }
    System.out.println(Google.getPath(c1,c1));


  } //main method ends
}//main class ends here