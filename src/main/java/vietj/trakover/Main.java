/*
 * Copyright (C) 2013 Julien Viet.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package vietj.trakover;

import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.Mp3File;

import java.io.File;

/** @author Julien Viet */
public class Main {

  /** . */
  private static final int KB = 1024;

  /** . */
  private static final int MB = 1024 * KB;

  public static void main(String[] args) throws Exception {
    if (args.length >= 1) {
      File root = new File(args[0]);
      if (root.exists()) {
        if (root.isDirectory()) {
          int count = scan(new File(root.getAbsolutePath()));
          System.out.println("Scanned " + count + " files");
        }
        else {
          System.out.println("File " + args[0] + " is not a directory");
        }
      }
      else {
        System.out.println("File " + args[0] + " does not exist");
      }
    }
    else {
      System.out.println("Please provide the directory to use");
    }
  }

  private static int scan(File dir) {
    int count = 0;
    if (!dir.getName().startsWith(".")) {
      File[] children = dir.listFiles();
      if (children != null) {
        for (File child : children) {
          if (child.isDirectory()) {
            count += scan(child);
          }
          else {
            String path = child.getAbsolutePath();
            int index = path.lastIndexOf('.');
            if (index > 0) {
              String ext = path.substring(index + 1);
              if (ext.equalsIgnoreCase("mp3")) {
                try {
                  Mp3File song = new Mp3File(path);
                  if (song.hasId3v2Tag()){
                    ID3v2 id3v2tag = song.getId3v2Tag();
                    byte[] bytes = id3v2tag.getAlbumImage();
                    if (bytes != null && bytes.length > MB) {
                      System.out.println("File: " + path + " exceeds 1MB");
                    }
                  }
                  count++;
                }
                catch (Exception e) {
                  System.err.println("Skipping: " + path);
                  e.printStackTrace(System.err);
                }
              }
            }
          }
        }
      }
    }
    return count;
  }
}
