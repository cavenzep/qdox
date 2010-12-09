package com.thoughtworks.qdox.model;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import java.util.LinkedList;
import java.util.List;

import com.thoughtworks.qdox.io.IndentBuffer;

public abstract class AbstractJavaEntity<T extends AbstractJavaEntity> extends AbstractBaseJavaEntity implements Comparable<T>, JavaModel {

    private List<String> modifiers = new LinkedList<String>();
    private String comment;
    private List<DocletTag> tags = new LinkedList<DocletTag>();
    
    private JavaClass parentClass;
    /**
     * Return list of modifiers as Strings.
     * (public, private, protected, final, abstract, static)
     */
    public List<String> getModifiers() {
        return modifiers;
    }

    /* (non-Javadoc)
     * @see com.thoughtworks.qdox.model.JavaModel#getComment()
     */
    public String getComment() {
        return comment;
    }

    /* (non-Javadoc)
     * @see com.thoughtworks.qdox.model.JavaModel#getTags()
     */
    public List<DocletTag> getTags() {
        return tags;
    }

    /* (non-Javadoc)
     * @see com.thoughtworks.qdox.model.JavaModel#getTagsByName(java.lang.String)
     */
    public List<DocletTag> getTagsByName(String name) {
        List<DocletTag> specifiedTags = new LinkedList<DocletTag>();
        for ( DocletTag docletTag : tags ) {
            if (docletTag.getName().equals(name)) {
                specifiedTags.add(docletTag);
            }
        }
        return specifiedTags;
    }

    /* (non-Javadoc)
     * @see com.thoughtworks.qdox.model.JavaModel#getTagByName(java.lang.String)
     */
    public DocletTag getTagByName(String name) {
        for (DocletTag docletTag : tags) {
            if (docletTag.getName().equals(name)) {
                return docletTag;
            }
        }
        return null;
    }

    /**
     * Convenience method for <code>getTagByName(String).getNamedParameter(String)</code>
     * that also checks for null tag.
     * @since 1.3
     */
    public String getNamedParameter(String tagName, String parameterName) {
        DocletTag tag = getTagByName(tagName);
        if(tag != null) {
            return tag.getNamedParameter(parameterName);
        } else {
            return null;
        }
    }

    void commentHeader(IndentBuffer buffer) {
        if (comment == null && tags.isEmpty()) {
            return;
        } else {
            buffer.write("/**");
            buffer.newline();

            if (comment != null && comment.length() > 0) {
                buffer.write(" * ");
                
                buffer.write(comment.replaceAll("\n", "\n * "));
                
                buffer.newline();
            }

            if (!tags.isEmpty()) {
                if (comment != null && comment.length() > 0) {
                    buffer.write(" *");
                    buffer.newline();
                }
                for (DocletTag docletTag : tags) {
                    buffer.write(" * @");
                    buffer.write(docletTag.getName());
                    if (docletTag.getValue().length() > 0) {
                        buffer.write(' ');
                        buffer.write(docletTag.getValue());
                    }
                    buffer.newline();
                }
            }

            buffer.write(" */");
            buffer.newline();
        }
    }

    public void setModifiers(List<String> modifiers) {
        this.modifiers = modifiers;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setTags(List<DocletTag> tagList) {
        this.tags = tagList;
    }

    //helper methods for querying the modifiers
    public boolean isAbstract() {
        return isModifierPresent("abstract");
    }

    public boolean isPublic() {
        return isModifierPresent("public");
    }

    public boolean isPrivate() {
        return isModifierPresent("private");
    }

    public boolean isProtected() {
        return isModifierPresent("protected");
    }

    public boolean isStatic() {
        return isModifierPresent("static");
    }

    public boolean isFinal() {
        return isModifierPresent("final");
    }

    public boolean isSynchronized() {
        return isModifierPresent("synchronized");
    }

    public boolean isTransient() {
        return isModifierPresent("transient");
    }

	/**
	 * @since 1.4
	 */
    public boolean isVolatile() {
        return isModifierPresent("volatile");
    }

	/**
	 * @since 1.4
	 */
    public boolean isNative() {
        return isModifierPresent("native");
    }

	/**
	 * @since 1.4
	 */
    public boolean isStrictfp() {
        return isModifierPresent("strictfp");
    }

    private boolean isModifierPresent(String modifier) {
        return modifiers.contains(modifier);
    }

    protected void writeNonAccessibilityModifiers(IndentBuffer result) {
        // modifiers (anything else)
        for (String modifier : modifiers) {
        	// check for public, protected and private
            if (!modifier.startsWith("p")) {
                result.write(modifier);
                result.write(' ');
            }
        }
    }

    protected void writeAccessibilityModifier(IndentBuffer result) {
        for (String modifier : modifiers) {
        	// check for public, protected and private
            if (modifier.startsWith("p")) {
                result.write(modifier);
                result.write(' ');
            }
        }
    }

    protected void writeAllModifiers(IndentBuffer result) {
        for (String modifier  : modifiers) {
            result.write(modifier);
            result.write(' ');
        }
    }
    
    /* (non-Javadoc)
     * @see com.thoughtworks.qdox.model.JavaModel#getSource()
     */
    public JavaSource getSource() { 
        return parentClass.getParentSource(); 
    }

    public void setParentClass( JavaClass parentClass )
    {
        this.parentClass = parentClass;
    }
    
    public JavaClass getParentClass()
    {
        return parentClass;
    }
}
