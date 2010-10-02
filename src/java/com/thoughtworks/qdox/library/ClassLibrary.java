package com.thoughtworks.qdox.library;

import java.io.Serializable;

import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaPackage;
import com.thoughtworks.qdox.model.JavaSource;

public interface ClassLibrary
    extends Serializable
{
    public JavaClass getJavaClass( String name );

    public JavaClass[] getClasses();
    
    public JavaSource[] getSources();
    
    public boolean hasJavaClass( String name );
    
    public JavaPackage getJavaPackage( String name );

    public JavaPackage[] getPackages();
}
