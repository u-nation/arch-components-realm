#! /bin/sh
exec ruby -S -x "$0" "$@"
#! ruby

state = 0
while str = STDIN.gets
  break if str.chomp == "exit"
  case state
  when 0
    state = 1 if str.match(/.*Found data binding errors.*/)
  when 1
    state = 2 if str.match(/.*e: .*/)
    next if state == 2
    print str
  end
end