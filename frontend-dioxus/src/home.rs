use crate::{
    api::{search, Text},
    common::{SearchBar, SearchItem},
    Route,
};

use dioxus::prelude::*;

#[component]
pub fn Home() -> Element {
    let search_text = use_signal(|| "".to_string());
    let search_items = use_resource(move || async move {
        if search_text().is_empty() {
            None
        } else {
            search(search_text()).await
        }
    });
    let search_items = match &*search_items.read_unchecked() {
        Some(res) => res.clone(),
        None => None,
    };

    let nav = navigator();
    let oncommit = use_callback(move |_: ()| {
        nav.push(Route::Search { q: search_text() });
    });

    rsx! {
        div {
            class: "w-screen flex-1 flex flex-col justify-center items-center space-y-4",
            div {
                class: "flex flex-col space-y-2 items-center justify-center",
                div {
                    class: "text-5xl font-bold",
                    "Tangut"
                }
                div {
                    "Онлайн-библиотека и словарь тангутского языка"
                }
            }
            div {
                class: "relative flex flex-col w-full max-w-[960px] space-y-4 place-self-center",
                SearchBar { search_text: search_text, oncommit: oncommit, }

                if let Some(items) = search_items {
                    SearchCard { items: items }
                }
            }
        }
    }
}

#[component]
fn SearchCard(items: Vec<Text>) -> Element {
    rsx! {
        div {
            class: "absolute top-full flex flex-col space-y-1",
            for item in items.iter().take(3) {
                SearchItem {text: item.clone()}
            }
        }
    }
}
