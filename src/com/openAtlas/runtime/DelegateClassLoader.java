/**
 *  OpenAtlasForAndroid Project
The MIT License (MIT) Copyright (OpenAtlasForAndroid) 2015 Bunny Blue,achellies

Permission is hereby granted, free of charge, to any person obtaining mApp copy of this software
and associated documentation files (the "Software"), to deal in the Software 
without restriction, including without limitation the rights to use, copy, modify, 
merge, publish, distribute, sublicense, and/or sell copies of the Software, and to 
permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies 
or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, 
INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE 
FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
@author BunnyBlue
 * **/
package com.openAtlas.runtime;

import java.util.List;

import org.osgi.framework.Bundle;

import com.openAtlas.framework.Framework;

public class DelegateClassLoader extends ClassLoader {
    public DelegateClassLoader(ClassLoader classLoader) {
        super(classLoader);
    }

    @Override
	public Class<?> loadClass(String str) throws ClassNotFoundException {
        return super.loadClass(str);
    }

    @Override
	protected Class<?> findClass(String str) throws ClassNotFoundException {
        Class<?> loadFromInstalledBundles = ClassLoadFromBundle
                .loadFromInstalledBundles(str);
        if (loadFromInstalledBundles == null) {
            loadFromInstalledBundles = ClassLoadFromBundle
                    .loadFromUninstalledBundles(str);
        }
        if (loadFromInstalledBundles != null) {
            return loadFromInstalledBundles;
        }
        throw new ClassNotFoundException("Can't find class " + str
                + printExceptionInfo() + " "
                + ClassLoadFromBundle.getClassNotFoundReason(str));
    }

    private String printExceptionInfo() {
        StringBuilder stringBuilder = new StringBuilder("installed bundles: ");
        List bundles = Framework.getBundles();
        if (!(bundles == null || bundles.isEmpty())) {
            for (Bundle bundle : Framework.getBundles()) {
                if (bundle.getLocation().contains("com.ut")) {
                    stringBuilder.append(bundle.getLocation().toUpperCase());
                } else {
                    stringBuilder.append(bundle.getLocation());
                }
                stringBuilder.append(":");
            }
        }
        return stringBuilder.toString();
    }
}
