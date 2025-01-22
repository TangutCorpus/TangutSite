use crate::api::search;
use crate::common::{SearchBar, SearchItem};
use dioxus::prelude::*;

#[component]
pub fn Search(q: String) -> Element {
    let input_text = use_signal(|| q.clone());
    let mut search_text = use_signal(|| q.clone());
    let search_items = use_resource(move || async move { search(search_text()).await });

    let search_items = match &*search_items.read_unchecked() {
        Some(res) => res.clone(),
        None => None,
    };

    let oncommit = use_callback(move |_: ()| {
        search_text.set(input_text());
    });

    rsx! {
        div {
            class: "max-w-[960px] w-full place-self-center flex flex-col space-y-8 p-2",
            div {
                class: "text-4xl font-bold place-self-center",
                "Библиотека"
            }
            div {
                class: "flex flex-col w-full space-y-4",
                SearchBar {
                    search_text: input_text,
                    oncommit: oncommit,
                }
                {
                    match search_items {
                        None => rsx! { div { class: "loading", }},
                        Some(texts) if texts.is_empty() => rsx! {div { "No texts found" }},
                        Some(texts) => rsx! {
                            div {
                                class: "flex flex-col space-y-1",
                                for text in texts {
                                    SearchItem {
                                        text: text,
                                    }
                                }
                            }
                        },
                    }
                }
            }
        }
    }
}
