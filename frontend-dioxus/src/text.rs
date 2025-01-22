use crate::api::get_by_id;
use dioxus::prelude::*;

#[component]
pub fn Text(id: i32) -> Element {
    let text = use_resource(move || async move { get_by_id(&id).await });
    let text = match &*text.read_unchecked() {
        Some(res) => res.clone(),
        None => None,
    };

    rsx! {
        if let Some(text) = text {
            div {
                class: "w-full max-w-[960px] place-self-center flex-1 flex flex-col justify-top items-center p-2 space-y-8",
                div {
                    class: "text-4xl font-bold",
                    {text.comment}
                }
                div {
                    class: "flex flex-row max-w-[960px] justify-center space-x-8",
                    ImageCarousel{
                        images: vec![
                            "https://upload.wikimedia.org/wikipedia/commons/thumb/b/bb/Chrysographic_Tangut_Golden_Light_Sutra.jpg/1280px-Chrysographic_Tangut_Golden_Light_Sutra.jpg".into(),
                            "https://upload.wikimedia.org/wikipedia/commons/thumb/e/e8/Fanhan_heshi_zhangzhongzhu.jpg/1024px-Fanhan_heshi_zhangzhongzhu.jpg".into(),
                        ],
                        class: "flex-1",
                    }
                    div {
                        class: "flex-1 flex flex-col border border-zink-200 rounded-md px-3 py-2 space-y-2",
                        div {
                            class: "font-bold",
                            "Основная информация"
                        }
                        InfoRow {  }
                        InfoRow {  }
                        InfoRow {  }
                        InfoRow {  }
                        InfoRow {  }
                    }
                }
                div {
                    class: "flex flex-col space-y-4 w-full",
                    div {
                        class: "font-bold text-4xl place-self-center",
                        "Переводы"
                    }
                    div {
                        class: "w-full flex flex-col space-y-1",
                        TranslationCard {}
                        TranslationCard {}
                        TranslationCard {}
                        TranslationCard {}
                    }
                }
            }
        }
    }
}

#[component]
fn ImageCarousel(images: Vec<String>, class: String) -> Element {
    let images_len = images.len();
    let mut image_index: Signal<usize> = use_signal(|| 0);
    let next_image = move |_| {
        image_index.set((image_index() + 1) % images_len);
    };
    let prev_image = move |_| {
        image_index.set((image_index() + images_len - 1) % images_len);
    };
    let url = use_memo(move || images[image_index()].clone());
    rsx! {
        div {
            class: "flex flex-row {class}",
            div {
                onclick: prev_image,
                class: "m-auto border border-zink-200 rounded-full p-2 flex justify-center items-center",
                span {
                    class: "material-symbols-outlined text-3xl",
                    "chevron_left"
                }
            }
            div {
                img {
                    src: url,
                }
            }
            div {
                onclick: next_image,
                class: "m-auto border border-zink-200 rounded-full p-2 flex justify-center items-center",
                span {
                    class: "material-symbols-outlined text-3xl",
                    "chevron_right"
                }
            }
        }
    }
}

#[component]
fn InfoRow() -> Element {
    rsx! {
        div {
            class: "w-full flex flex-row",
            div {
                class: "",
                "Автор"
            }
            div { class: "flex-1 h-0" }
            div {
                class: "",
                "Вася Пупкин"
            }
        }
    }
}

#[component]
fn TranslationCard() -> Element {
    rsx! {
        div {
            class: "flex flex-col space-y-1 rounded-md border border-zink-200 px-3 py-2",
            div {
                class: "font-bold",
                "Русский"
            }
            div {
                "Лорем ипсум долор сит амет"
            }
        }
    }
}
