# Markdown-Dateien

Markdown-Dateien (.md) bieten eine übersichtliche und strukturierte Lösung der Programmdokumentation.
Sie sind auch in Rohform lesbar, jedoch bietet es sich an eine Markdown Preview zu verwenden.
Eine solche Preview ist in GitHub integriert, also empfiehlt es sich die Markdown-Dateien auf GitHub einzusehen.

Für das Bearbeiten solcher Dateien eignet es sich einen online Markdown-Editor zu verwenden.  
[Markdown Live Preview](https://markdownlivepreview.com/) eignet sich dafür besonders. Die
dort bereitgestellte Vorlage findet sich auch hier noch einmal. Auf GitHub lässt sich der
Markdown-Quelltext über den `Code`-Button direkt oben-links über dem Dokument anzeigen.

# Markdown syntax guide

## Headers

# This is a Heading h1
## This is a Heading h2
###### This is a Heading h6

## Emphasis

*This text will be italic*  
_This will also be italic_

**This text will be bold**  
__This will also be bold__

_You **can** combine them_

## Lists

### Unordered

* Item 1
* Item 2
* Item 2a
* Item 2b
    * Item 3a
    * Item 3b

### Ordered

1. Item 1
2. Item 2
3. Item 3
    1. Item 3a
    2. Item 3b

## Images

![This is an alt text.](../../src/main/resources/assets/background/example.png "This is a sample image.")

## Links

You may be using [Markdown Live Preview](https://markdownlivepreview.com/).

## Blockquotes

> Markdown is a lightweight markup language with plain-text-formatting syntax, created in 2004 by John Gruber with Aaron Swartz.
>
>> Markdown is often used to format readme files, for writing messages in online discussion forums, and to create rich text using a plain text editor.

## Tables

| Left columns  | Right columns |
| ------------- |:-------------:|
| left foo      | right foo     |
| left bar      | right bar     |
| left baz      | right baz     |

## Blocks of code

```
let message = 'Hello world';
alert(message);
```

## Inline code

This web site is using `markedjs/marked`.