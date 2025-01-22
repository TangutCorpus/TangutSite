use crate::common::Navbar;
use crate::home::Home;
use crate::not_found::NotFound;
use crate::search::Search;
use crate::text::Text;
use dioxus::prelude::*;
mod api;
mod common;
mod home;
mod not_found;
mod search;
mod text;

#[derive(Debug, Clone, Routable, PartialEq)]
#[rustfmt::skip]
enum Route {
    #[layout(Scaffold)]
    #[route("/")]
    Home {},
    #[route("/text?:q")]
    Search { q: String },
    #[route("/text/:id")]
    Text { id: i32 },
    #[route("/not-found")]
    NotFound {},
}

const FAVICON: Asset = asset!("/assets/favicon.ico");
const MAIN_CSS: Asset = asset!("/assets/main.css");
const TAILWIND_CSS: Asset = asset!("/assets/tailwind.css");

fn main() {
    dioxus::launch(App);
}

#[component]
fn App() -> Element {
    rsx! {
        document::Link { rel: "icon", href: FAVICON }
        document::Link { rel: "stylesheet", href: MAIN_CSS } document::Link { rel: "stylesheet", href: TAILWIND_CSS }
        document::Link { rel: "stylesheet", href: "https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined" }
        Router::<Route> {}
    }
}

#[component]
fn Scaffold() -> Element {
    rsx! {
        div {
            class: "w-screen h-screen bg-zink-50 text-zink-950 font-sans flex flex-col",
            Navbar { is_logged_in: false }
            Outlet::<Route> {}
        }
    }
}
