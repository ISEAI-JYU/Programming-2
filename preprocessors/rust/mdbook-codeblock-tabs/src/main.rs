use mdbook::BookItem;
use mdbook::book::{Book, Chapter};
use mdbook::errors::Result;
use mdbook::preprocess::{CmdPreprocessor, Preprocessor, PreprocessorContext};
use pulldown_cmark::{CodeBlockKind, Event, Parser, Tag, TagEnd};
use std::io;

fn main() {
    let mut args = std::env::args().skip(1);
    match args.next().as_deref() {
        Some("supports") => {
            // Supports all renderers.
            return;
        }
        Some(arg) => {
            eprintln!("unknown argument: {arg}");
            std::process::exit(1);
        }
        None => {}
    }

    if let Err(e) = handle_preprocessing() {
        eprintln!("{e}");
        std::process::exit(1);
    }
}

pub fn handle_preprocessing() -> Result<()> {
    let pre = CodeBlockTabsPreprocessor;
    let (ctx, book) = CmdPreprocessor::parse_input(io::stdin())?;

    let processed_book = pre.run(&ctx, book)?;
    serde_json::to_writer(io::stdout(), &processed_book)?;

    Ok(())
}

struct CodeBlockTabsPreprocessor;


fn create_tabbed_codeblocks(chapter: &mut Chapter) {
    let mut buf = String::with_capacity(chapter.content.len());
    let parser = Parser::new(&chapter.content);
    let mut current_codeblock_lang = None;

    let parser = parser.map(|event| match &event {
        Event::Start(Tag::CodeBlock(CodeBlockKind::Fenced(lang))) => {
            current_codeblock_lang = Some(lang.to_string());
            event
        },
        Event::Text(text) => match &current_codeblock_lang {
            Some(lang) => {
                eprintln!("Got code block with lang = {}: {}", lang, text);
                event
            },
            None => event
        },
        Event::End(TagEnd::CodeBlock) =>  {
            current_codeblock_lang = None;
            event
        }
        _ => event,
    });

    match pulldown_cmark_to_cmark::cmark(parser, &mut buf) {
        Ok(_) => chapter.content = buf,
        Err(e) => eprintln!("Error converting markdown: {}", e),
    }
}

impl Preprocessor for CodeBlockTabsPreprocessor {
    fn name(&self) -> &str {
        "codeblock-tabs"
    }

    fn run(&self, _ctx: &PreprocessorContext, mut book: Book) -> Result<Book> {
        book.for_each_mut(|item| match item {
            BookItem::Chapter(ch) =>  create_tabbed_codeblocks(ch),
            _ => {}
        });
        Ok(book)
    }
}