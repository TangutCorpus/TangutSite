<template>
  <div class="relative">
    <dt>
      <div class="absolute flex items-center justify-center h-12 w-12 rounded-md bg-indigo-500 text-white">
        <slot name="icon"></slot>
      </div>
      <p class="ml-16 text-lg leading-6 font-medium text-gray-900">
        <slot name="title"></slot>
      </p>
    </dt>
    <dd class="mt-2 ml-16 text-base text-gray-500">
      <slot name="description"></slot>
    </dd>
  </div>
</template>