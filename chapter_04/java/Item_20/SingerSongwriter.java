package chapter_04.java.Item_20;

/*
 *  Singer와 Songwriter 모두를 확장하고 새로운 메서드까지 추가한 제3의 인터페이스
 */
public interface SingerSongwriter extends Singer, Songwriter{
    AudioClip strum();
    void actSensitive();
}