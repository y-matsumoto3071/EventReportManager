## ソースのフォーマット
### 1. タブ
タブの長さは半角スペース4文字とする。

###  2. 行の長さ
1行の長さは半角80文字以下とする。

###  3. 行の区切り位置
行が複数にわたる場合、カンマの後、演算子の前の適用な場所で改行する。<br>
文の階層構造からみて、なるべく高いレベルでの区切り位置を採用する。<br>
改行後の新しい行は、前の行の同じレベルの記述とインデントを合わせる。<br>
前の行とインデントを合わせるとコードが読みづらくなる場合は、単にタブ長と<br>
同じ長さでインデントする。

### 4. 空白行
 1. 次の場合は、空白行を1行入れる。

    1. メソッドの間
    1. メソッドの局所変数宣言と最初の文との間
    1. ブロックコメント、行コメントの前
    1. メソッド内の論理的なブロックの間
 1. 次の場合には、2行の空白行を入れる。

       1. ソースのセクション間
       1. クラスやインターフェースの定義の間

### 5. スペース
 1. 次の場合にはスペースを挿入する。

    1. キーワードとそれに続く括弧との間(ただし、メソッド名とその引数との間は除く)
    1. 引数の並びにおいて、カンマの後
    1. 「.(ドット)」を除くバイナリ演算子とそのオペランドとの間(ただし、単項演算子を除く)
    1. or文の小括弧内における、複数文の間
    1. キャスト演算子の後

    [目次に戻る](CONTENTS.md)
