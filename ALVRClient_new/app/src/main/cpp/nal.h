#ifndef ALVRCLIENT_NAL_H
#define ALVRCLIENT_NAL_H

#include <jni.h>
#include <list>
#include "utils.h"
#include "fec.h"


class NALParser {
public:
    NALParser(JNIEnv *env, jobject udpManager, UdpManager *udpManager_C);
    ~NALParser();

    void reset();

    void setCodec(int codec);
    bool processPacket(VideoFrame *packet, int packetSize);
private:
    void push(const char *buffer, int length, uint64_t frameIndex);
    int findVPSSPS(const char *frameBuffer, int frameByteSize);

    FECQueue m_queue;

    int m_codec = 1;

    JNIEnv *m_env;
    jobject mUdpManager;

    jfieldID NAL_length;
    jfieldID NAL_frameIndex;
    jfieldID NAL_buf;

    jmethodID mObtainNALMethodID;
    jmethodID mPushNALMethodID;

    bool mIDRProcessed = false;
};
#endif //ALVRCLIENT_NAL_H
