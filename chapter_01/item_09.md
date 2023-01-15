# Item09. try-finally 보다는 try-with-resources를 사용하라

[메인페이지 이동](../README.md)

```
try-finally : 자원을 자동으로 반환하지 않아 따로 닫아줘야한다.
try-with-resource : 코드는 더 짧고 분명해지고 자원을 자동 반환 해준다.

주로 IOStream, DB Connection 에서 유용하게 사용
```

## 기존 사용방법

---

> 자원갯수가 증가하면 할수록 코드는 더욱 지저분해지고 신경쓸 부분이 많아진다.

```
static void copy2(String src, String dst) throws IOException{
        InputStream in = null;
        OutputStream out = null;
        try{
            in = new FileInputStream(src);
            out = new FileOutputStream(dst);

            byte[] buf = new byte[1111];
            int n;
            while( (n=in.read(buf)) >=0){
                out.write(buf, 0, n);
            }
        }finally {
            if(in!=null){
                in.close();
            }
            if (out != null) {
                out.close();
            }
        }
    }
```

## 간단 사용 방법

---

> 자원의 갯수와 상관없이 자동으로 자원을 반환해주기 때문에 신경쓸 부분이 덜하다

```
static void copy(String src, String dst) throws IOException{

try(
    InputStream in = new FileInputStream(src);
    OutputStream out = new FileOutputStream(dst);
    ){
      byte[] buf = new byte[BUFFER_SIZE];
      int n;
      while( (n=in.read(buf)) >=0){
        out.write(buf, 0, n);
      }
    }
}
```
