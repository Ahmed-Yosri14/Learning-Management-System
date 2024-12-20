#include <iostream>
using namespace std;

class SuffixArray
{
    class String
    {
        size_t length;
        const char *chars;

        public:
        String(const char *chars): chars(chars)
        {
            length = 0;
            while (chars[length] != '\0')
                length++;
        };
        size_t size(){ return length; }
        char operator[](size_t pos){ return chars[pos]; }
    };
    void resize(size_t *&ptr){ resize(ptr, n); }
    void resize(size_t *&ptr, size_t sz){ ptr = new size_t[sz]{}; }
    pair<size_t, size_t> getPair(size_t i, size_t k)
    {
        if (k == 0)
            return {s[i], s[i]};
        return {pos[i], pos[(i + k) % n]}; 
    }
    void sort(size_t *ptr, size_t sz, size_t k)
    {
        if (sz <= 1)
            return;
        size_t n = sz / 2, m = sz - n;
        size_t *leftPtr, *rightPtr;
        resize(leftPtr, n), resize(rightPtr, m);

        for (size_t i{}; i < n; i++)
            leftPtr[i] = ptr[i];
        for (size_t i{}; i < m; i++)
            rightPtr[i] = ptr[i + n];
        
        sort(leftPtr, n, k), sort(rightPtr, m, k);
        
        for (size_t i{}, j{}; i + j < sz;)
        {
            if (j == m || (i != n && getPair(leftPtr[i], k) <= getPair(rightPtr[j], k)))
                ptr[i + j] = leftPtr[i], i++;
            else
                ptr[i + j] = rightPtr[j], j++;
        }
    }

    size_t n;
    String s;
    size_t *suf, *pos;

    public:
    SuffixArray() = default;
    SuffixArray(String s): s(s), n(s.size()){}
    void ConstructUsingPrefixDoubling()
    {
        size_t *nPos;
        resize(suf), resize(pos), resize(nPos);
        for (size_t i{}; i < n; i++)
            suf[i] = i;

        sort(suf, n, 0);

        for (size_t i{}, cls{}; i < n; i++)
        {
            if (i && s[suf[i]] != s[suf[i - 1]])
                cls++;
            pos[suf[i]] = cls;
        }

        for (size_t k{1}; k < n; k <<= 1)
        {
            for (size_t i{}; i < n; i++)
                suf[i] = i;

            sort(suf, n, k);
            
            size_t *cuPos;
            resize(cuPos);
            size_t cls = 0;
            for (size_t i{}; i < n; i++)
            {
                if (i && getPair(suf[i], k) != getPair(suf[i - 1], k))
                    cls++;
                cuPos[suf[i]] = cls;
            }
            
            for (size_t i{}; i < n; i++)
                pos[i] = cuPos[i];
            
            delete [] cuPos;

            if (cls == n - 1)
                break;
        }
    }
    void Print()
    {
        for (size_t i{}; i < n; i++)
            cout << suf[i] << " ";
    }
};

int main()
{
    SuffixArray t("ACGACTACGATAAC$");

    t.ConstructUsingPrefixDoubling();

    t.Print(); // Prints:  14 11 12  0  6  3  9 13  1  7  4  2  8 10  5

    return 0;
}