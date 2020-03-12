//
// Created by vasa on 4/8/19.
//

#include <jni.h>
#include <string>

using namespace std;

// the magic string used to identify a request came from the app
extern "C" JNIEXPORT jstring JNICALL Java_cz_lunakv_fmatfyz_PlayerService_getMagic(JNIEnv *env, jobject thisObj) {
    string magic = "* replace by actual magic string *";
    return env->NewStringUTF(magic.c_str());
}

// address where tokens can be obtained
extern "C" JNIEXPORT jstring JNICALL Java_cz_lunakv_fmatfyz_PlayerService_getTokenUrl(JNIEnv *env, jobject thisObj) {
    string magic = "* replace by token generating server endpoint *";
    return env->NewStringUTF(magic.c_str());
}

// address where received messages are reported
extern "C" JNIEXPORT jstring JNICALL Java_cz_lunakv_fmatfyz_PlayerService_getMessageUrl(JNIEnv *env, jobject thisObj) {
    string magic = "* replace by on-receive confirmation endpoint *";
    return env->NewStringUTF(magic.c_str());
}
