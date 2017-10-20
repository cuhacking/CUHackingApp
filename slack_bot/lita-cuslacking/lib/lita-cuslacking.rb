require "lita"

Lita.load_locales Dir[File.expand_path(
  File.join("..", "..", "locales", "*.yml"), __FILE__
)]

require "lita/handlers/cuslacking_chat"
require "lita/handlers/cuslacking_http"

Lita::Handlers::CUSlackingChat.template_root File.expand_path(
  File.join("..", "..", "templates"),
 __FILE__
)

Lita::Handlers::CUSlackingHTTP.template_root File.expand_path(
  File.join("..", "..", "templates"),
 __FILE__
)
