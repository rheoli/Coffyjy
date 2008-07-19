package net.rheoli.Coffyjy.ConstantPool;

import java.io.*;

public class cCONSTANT_Ref_Info extends cCONSTANTPool
{
  int iClassIndex;
  int iNameAndTypeIndex;
  
  public cCONSTANT_Ref_Info ()
  {
    iClassIndex       = 0;
    iNameAndTypeIndex = 0;
  }
  
  public void setRefInfo ( int _iTag, int _iCIndex, int _iNATIndex )
  {
    setTag ( _iTag );
    iClassIndex       = _iCIndex;
    iNameAndTypeIndex = _iNATIndex;
  }
  
  public int getIndex ()
  {
    return ( iClassIndex );
  }
  
  public int getNameAndTypeIndex ()
  {
    return ( iNameAndTypeIndex );
  }
}
