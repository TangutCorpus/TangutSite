use dioxus::prelude::*;

#[component]
pub fn NotFound() -> Element {
    rsx! {
        div {
            class: "w-screen bg-zink-50 text-zink-950 font-sans h-screen flex-1 flex flex-col justify-center items-center space-y-4",
            div {
                class: "text-4xl",
                "Page not Found"
            }
        }
    }
}
