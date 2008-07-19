package net.rheoli.Coffyjy.ConstantPool;

import java.io.*;

public class cCONSTANT_Class_Info extends cCONSTANTPool
{
  int iNameIndex;
  
  public cCONSTANT_Class_Info ()
  {
    iNameIndex = 0;
  }
  
  public void setClassInfo ( int _iIndex )
  {
    setTag ( CONSTANT_Class );
    iNameIndex = _iIndex;
  }
  
  public int getIndex ()
  {
    return ( iNameIndex );
  }
}
