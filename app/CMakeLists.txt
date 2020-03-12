cmake_minimum_required(VERSION 3.6)

add_library( # Specifies the name of the library.
             magician

             # Sets the library as a shared library.
             SHARED

             # Provides a relative path to your source file(s).
             src/main/cpp/magician.cpp )

include_directories(src/main/cpp/include/)

find_library( # Defines the name of the path variable that stores the
              # location of the NDK library.
              log-lib

              # Specifies the name of the NDK library that
              # CMake needs to locate.
              log )
