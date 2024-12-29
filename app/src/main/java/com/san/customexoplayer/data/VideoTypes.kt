package com.san.customexoplayer.data

enum class VideoTypes(val type: String, val path: String) {
    OFFLINE("Local video", "asset:///local_video.mkv"),
    ONLINE("Remote video", "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"),
    DASH("DASH", "https://bitmovin-a.akamaihd.net/content/MI201109210084_1/mpds/f08e80da-bf1d-4e3d-8899-f0f6155f6efa.mpd"),
    HLS("HLS", "https://devstreaming-cdn.apple.com/videos/streaming/examples/img_bipbop_adv_example_fmp4/master.m3u8"),
    DRM_SUPPORT("DRM Support", "https://cdn.bitmovin.com/content/assets/art-of-motion_drm/mpds/11331.mpd"),
    SMOOTH_STREAMING("Smooth Streaming", "https://playready.directtaps.net/smoothstreaming/SSWSS720H264/SuperSpeedway_720.ism/Manifest");

    override fun toString(): String {
        return type
    }
}