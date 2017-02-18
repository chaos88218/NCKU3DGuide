package com.example.miles.ncku3dguide.MapNavi;


public class Nodes {

    public int ind = -1;
    public String name;
    public Points data;

    public Nodes(){
        data = new Points();
        name = "Chinese";
    };
    public void setNodes(Points P, int i)
    {
        data.x = P.x;
        data.y = P.y;
        ind = i;
    }
    public String getName()
    {
        return name;
    }
    public void setName(String n)
    {
        name = n;
    }
    public void showData()
    {
      //  Log("<< \"X:\" << data.x << \" Y:\" << data.y") ;
    }
}
