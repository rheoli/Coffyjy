package net.rheoli.Coffyjy.ConstantPool;

import java.io.*;

public class cCONSTANT_NameAndType_Info extends cCONSTANTPool
{
  int iNameIndex;
  int iDescriptorIndex;
  
  public cCONSTANT_NameAndType_Info ()
  {
    iNameIndex       = 0;
    iDescriptorIndex = 0;
  }
  
  public void setNameAndTypeInfo ( int _iNIndex, int _iDIndex )
  {
    setTag ( CONSTANT_NameAndType );
    iNameIndex       = _iNIndex;
    iDescriptorIndex = _iDIndex;
  }
  
  public int getIndex ()
  {
    return ( iNameIndex );
  }
  
  public int getDescriptorIndex ()
  {
    return ( iDescriptorIndex );
  }
}
