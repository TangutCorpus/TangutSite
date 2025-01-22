use crate::{api::Text, Route};
use dioxus::prelude::*;

#[component]
pub(crate) fn Navbar(is_logged_in: bool) -> Element {
    rsx! {
        div {
            class: "flex flex-row px-4 py-2 space-x-6 text-lg justify-end items-center",
            NavbarLink { to: Route::Home {},  "Главная" }
            NavbarLink { to: Route::Search { q: "".to_owned() }, "Библиотека" }
            NavbarLink { to: Route::NotFound {}, "Грамматика" }
            NavbarLink { to: Route::NotFound {}, "О нас" }
            if !is_logged_in {
                ButtonSecondary { onclick: |_| {},  "Вход" }
                ButtonPrimary { onclick: |_| {}, "Регистрация" }
            } else {
                ButtonPrimary { onclick: |_| {}, "Log out" }
            }
        }
    }
}

#[component]
fn NavbarLink(to: Route, children: Element) -> Element {
    rsx! {
        Link { to: to, {children} }
    }
}

#[component]
pub fn ButtonPrimary(children: Element, onclick: EventHandler<MouseEvent>) -> Element {
    rsx! {
        Button {
            class: "bg-zink-950 hover:bg-zink-800 text-zink-50",
            onclick: onclick,
            {children}
        }
    }
}

#[component]
fn ButtonSecondary(children: Element, onclick: EventHandler<MouseEvent>) -> Element {
    rsx! {
        Button {
            class: "bg-zink-100 hover:bg-zink-200 text-zink-950",
            onclick: onclick,
            {children}
        }
    }
}

#[component]
fn Button(children: Element, class: String, onclick: EventHandler<MouseEvent>) -> Element {
    rsx! {
        button {
            class: "flex px-4 py-2 rounded-md {class}",
            onclick: onclick,
            {children}
        }
    }
}

#[component]
pub fn SearchBar(search_text: Signal<String>, oncommit: EventHandler<()>) -> Element {
    rsx! {
        div {
            class: "flex flex-row space-x-2 w-full",
            input {
                class: "flex-1 bg-white border border-zink-200 rounded-md px-3 py-2 placeholder-zink-500",
                placeholder: "Поиск по текстам",
                value: "{search_text}",
                oninput: move |event| search_text.set(event.value()),
                onkeypress: move |event| {
                    if event.data.key() == Key::Enter {
                        oncommit.call(());
                    }
                },
            }
            ButtonPrimary {
                onclick: move |_| {
                    oncommit.call(());
                },
                span {
                    class: "material-symbols-outlined m-auto",
                    "search"
                }
            }
        }
    }
}

#[component]
pub fn SearchItem(text: Text) -> Element {
    rsx! {
        Link {
            to: Route::Text { id: text.id.unwrap_or(0) },
            div {
                class: "flex flex-col px-3 py-2 border border-zink-200 rounded-md",
                div {
                    class: "font-bold",
                    {text.comment}
                }
                div { class: "w-full h-1", }
                div { "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."}
                div { "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."}
            }
        }
    }
}
